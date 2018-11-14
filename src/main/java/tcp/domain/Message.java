package tcp.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import tcp.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static tcp.main.TCPConfig.*;

@Getter
public class Message {

    private String messageContent;

    private String id;

    @Setter
    private LocalDateTime timeSent;

    @Setter
    private LocalDateTime timeReplied;

    private DateUtils dateUtils;

    public Message(Integer size, LocalDateTime time) {
        super();
        dateUtils = DateUtils.getInstance();
        this.id = generateId();
        this.messageContent = generateContent(size, time);
    }

    private String generateId() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .substring(0, Integer.valueOf(getProperty(MESSAGE_ID_LENGTH)));
    }

    private String generateContent(Integer size, LocalDateTime time) {
        String content = id + getProperty(MESSAGE_DELIMITER) + dateUtils.getDateTimeFormatter.get().format(time);

        Integer repeatSize = size - Integer.valueOf(getProperty(MESSAGE_ID_LENGTH)) - dateUtils.getDateTimeLength.get() - 2 * getProperty(MESSAGE_DELIMITER).length();
        if (repeatSize > 0) {
            content = content + getProperty(MESSAGE_DELIMITER) + StringUtils.repeat("*", repeatSize);
        } else {
            content = content + getProperty(MESSAGE_DELIMITER);
        }
        return content;
    }

}
