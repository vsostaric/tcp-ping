package tcp.domain;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;

import static tcp.main.TCPConfig.MIN_MESSAGES_FOR_SPEED_UPDATE;
import static tcp.main.TCPConfig.getProperty;

public final class StatisticalData {

    private static StatisticalData instance;

    private int messagesSent;

    private int messagesNotReplied;

    private BigDecimal speed;

    private BigDecimal maximalTime;

    private BigDecimal fullReplyTime;

    private int messagesSentOnLastSpeedUpdate;

    private LocalTime lastSpeedUpdate;

    private static final Integer minMessagesForSpeedUpdate = Integer.valueOf(getProperty(MIN_MESSAGES_FOR_SPEED_UPDATE));

    private LocalTime start;

    private StatisticalData() {
        super();
        this.messagesSent = 0;
        this.speed = BigDecimal.ZERO;
        this.fullReplyTime = BigDecimal.ZERO;
        this.maximalTime = BigDecimal.ZERO;
        this.lastSpeedUpdate = LocalTime.now();
        this.messagesSentOnLastSpeedUpdate = 0;
    }

    public static StatisticalData getInstance() {
        if (instance == null) {
            synchronized (StatisticalData.class) {
                if (instance == null) {
                    instance = new StatisticalData();
                }
            }
        }
        return instance;
    }


    public void updateData(Message message) {

        synchronized (this) {

            addMessagesSent();

            if (messagesSent - messagesSentOnLastSpeedUpdate >= minMessagesForSpeedUpdate) {
                updateSpeed();
            }

            long completeRespondTime =
                    Duration.between(
                            message.getTimeSent(),
                            message.getTimeReplied()).toMillis();

            addToFullReplyTime(new BigDecimal(completeRespondTime));

            checkMaximalTime(new BigDecimal(completeRespondTime));

        }
    }

    public void messageNotReceived() {

        synchronized (this) {
            addMessagesNotReceived();
        }

    }

    public String showData() {
        return createDataMessage("<br>", true);
    }

    public void start() {
        this.start = LocalTime.now();
    }

    @Override
    public String toString() {
        return createDataMessage("\n", false);
    }

    private void addMessagesSent() {
        messagesSent += 1;
    }

    private void addMessagesNotReceived() {
        messagesNotReplied += 1;
    }

    private void updateSpeed() {
        LocalTime updateTime = LocalTime.now();
        BigDecimal time = new BigDecimal(Duration.between(lastSpeedUpdate, updateTime).getSeconds());

        if (BigDecimal.ZERO.equals(time)) {
            return;
        }

        BigDecimal speed = BigDecimal.valueOf(messagesSent - messagesSentOnLastSpeedUpdate).divide(time, 2,
                RoundingMode.DOWN);

        if (BigDecimal.ZERO.equals(speed)) {
            return;
        }

        this.speed = speed;
        this.lastSpeedUpdate = updateTime;
        this.messagesSentOnLastSpeedUpdate = messagesSent;

    }

    private void addToFullReplyTime(BigDecimal add) {
        fullReplyTime = fullReplyTime.add(add);
    }

    private void checkMaximalTime(BigDecimal time) {
        if (time.compareTo(maximalTime) > 0) {
            maximalTime = time;
        }
    }

    private String createDataMessage(String newLine, boolean isHtml) {

        StringBuilder builder = new StringBuilder();

        if (isHtml) {
            builder.append("<html>");
        }

        builder.append("Time: ");
        builder.append(
                DurationFormatUtils.formatDuration(Duration.between(start, LocalTime.now()).toMillis(), "HH:mm:ss"));
        builder.append(newLine);
        builder.append("messagesSent: ");
        builder.append(messagesSent);
        if (messagesSent > 0) {
            builder.append(newLine);
            builder.append("messages not replied: ");
            builder.append(messagesNotReplied);
            builder.append(newLine);
            builder.append("speed: ");
            builder.append(speed);
            builder.append(newLine);
            builder.append("maximal time: ");
            builder.append(maximalTime);
            builder.append(newLine);
            builder.append("full reply time: ");
            builder.append(fullReplyTime);
            builder.append(newLine);
            builder.append("Average Reply Time: (From Pitcher To Catcher and Back): ");
            builder.append(fullReplyTime.divide(new BigDecimal(messagesSent - messagesNotReplied), 2, RoundingMode.HALF_DOWN));
            builder.append(newLine);
        }

        if (isHtml) {
            builder.append("</html>");
        }

        return builder.toString();
    }

}
