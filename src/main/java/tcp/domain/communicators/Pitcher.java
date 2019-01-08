package tcp.domain.communicators;

import org.apache.commons.lang.StringUtils;
import tcp.domain.Message;
import tcp.domain.StatisticalData;
import tcp.factory.Args;
import tcp.frame.FrameController;
import tcp.utils.DateUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static tcp.main.TCPConfig.*;

public final class Pitcher implements Communicator {

    private static Pitcher instance;

    private Integer port;

    private Integer messagePerSecond;

    private Integer size;

    private String hostname;

    private Integer poolSize;

    private DateUtils dateUtils;

    private Pitcher(Args args) {
        super();
        this.port = args.getPort();
        this.messagePerSecond = args.getMps();
        this.size = (args.getSize() == null) ?
                Integer.valueOf(getProperty(DEFAULT_MESSAGE_SIZE)) : args.getSize();
        this.hostname = args.getHostname();
        this.poolSize = (args.getPitcherPoolSize() == null) ?
                Integer.valueOf(getProperty(MESSAGE_POOL_SIZE_DEFAULT)) :
                args.getPitcherPoolSize();
        this.dateUtils = DateUtils.getInstance();
    }

    public static Pitcher getInstance(Args args) {
        if (instance == null) {
            synchronized (Catcher.class) {
                if (instance == null) {
                    instance = new Pitcher(args);
                }
            }
        }
        return instance;
    }

    @Override
    public void communicate() throws IOException {

        startFrame();

        ConcurrentHashMap<String, Message> sentMessages = new ConcurrentHashMap<>();
        StatisticalData data = StatisticalData.getInstance();

        long delay = getDelay();

        data.start();
        Executors.newScheduledThreadPool(poolSize).scheduleAtFixedRate(() -> {
            try (Socket socket = new Socket(hostname, port)) {
                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

                LocalDateTime timeSent = LocalDateTime.now();
                Message message = new Message(size, timeSent);
                message.setTimeSent(timeSent);

                dOut.writeUTF(message.getMessageContent());

                sentMessages.put(message.getId(), message);
                dOut.flush();

                while (true) {

                    LocalTime start = LocalTime.now();

                    DataInputStream dIn = new DataInputStream(socket.getInputStream());
                    String receivedMessage = dIn.readUTF();
                    if (!StringUtils.isEmpty(receivedMessage)) {
                        String id = extractId(receivedMessage);
                        Message repliedMessage = sentMessages.get(id);
                        repliedMessage.setTimeReplied(LocalDateTime.now());

                        sentMessages.remove(repliedMessage.getId());
                        data.updateData(repliedMessage);
                        break;
                    } else if (Duration.between(start, LocalTime.now()).toMillis() > Long.valueOf(getProperty(MESSAGE_WAIT_FOR_REPLY))) {
                        data.messageNotReceived();
                        break;
                    }

                }

            } catch (IOException e) {
            }
        }, 0, delay, TimeUnit.MILLISECONDS);

        while (true) {

        }

    }

    @Override
    public void openFrame() {
        FrameController.getInstance().openFrame("Pitcher Frame", "Pitching to " + hostname + ":" + port, 50);
    }

    @Override
    public void updateFrame() {
        FrameController.getInstance().updateFrame(StatisticalData.getInstance().showData(), 20);
    }

    private long getDelay() {
        return 1000 / messagePerSecond;
    }

    private String extractId(String message) {
        return message.split(Pattern.quote(getProperty(MESSAGE_DELIMITER)))[0];
    }

    public static void dumpInstance() {
        instance = null;
    }

}
