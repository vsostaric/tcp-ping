package tcp.domain.communicators;

import org.apache.commons.lang.StringUtils;
import tcp.utils.DateUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static tcp.main.TCPConfig.MESSAGE_DELIMITER;
import static tcp.main.TCPConfig.getProperty;

final class CatcherResponder {

    private Socket socket;

    private DateUtils dateUtils;

    private Runnable runnable = () -> {
        try (DataInputStream dIn = new DataInputStream(socket.getInputStream());
             DataOutputStream dOut = new DataOutputStream(socket.getOutputStream())) {

            final String message = dIn.readUTF();
            if (StringUtils.isEmpty(message)) {
                return;
            }

            LocalDateTime start = LocalDateTime.now();
            LocalDateTime sentTime = dateUtils.extractTime.apply(message);

            String messageDelimiter = getProperty(MESSAGE_DELIMITER);
            String[] splitMessage = message.split(Pattern.quote(messageDelimiter));

            StringBuilder builder = new StringBuilder();
            builder.append(splitMessage[0]);
            builder.append(messageDelimiter);

            Duration duration = Duration.between(start, LocalDateTime.now());
            builder.append(dateUtils.getDateTimeFormatter.get().format(sentTime.plus(duration)));

            if (splitMessage.length > 2) {
                builder.append(messageDelimiter);
                builder.append(splitMessage[2]);
            }


            dOut.writeUTF(builder.toString());
            dOut.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    CatcherResponder(Socket socket) {
        this.socket = socket;
        this.dateUtils = DateUtils.getInstance();
    }

    void respond() {
        new Thread(runnable).start();
    }

}
