package tcp.domain;

import org.apache.commons.lang.StringUtils;
import tcp.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.UUID;

import static tcp.main.TCPConfig.*;

public class Message {

    private String messageContent;

    private String id;

    private LocalDateTime timeSent;

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

    public Temporal getTimeSent() {
        return timeSent;
    }

    public Temporal getTimeReplied() {
        return timeReplied;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getId() {
        return id;
    }

    public void setTimeReplied(LocalDateTime timeReplied) {
        this.timeReplied = timeReplied;
    }
}
