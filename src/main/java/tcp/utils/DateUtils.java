package tcp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static tcp.main.TCPConfig.*;

public final class DateUtils {

    public Supplier<DateTimeFormatter> getDateTimeFormatter = () -> DateTimeFormatter.ofPattern(messageDateTimePattern);

    public Supplier<Integer> getDateTimeLength = () -> messageDateTimePattern.length();

    private static String messageDateTimePattern = getProperty(MESSAGE_DATE_TIME_PATTERN);

    private static String messageDelimiter = getProperty(MESSAGE_DELIMITER);

    private static DateUtils instance;

    public Function<String, LocalDateTime> extractTime = (message) -> {
        String[] splittedMessage = message.split(Pattern.quote(messageDelimiter));
        String timeReceived = splittedMessage[1];
        return LocalDateTime.parse(timeReceived, getDateTimeFormatter.get());
    };


    public static DateUtils getInstance() {
        if (instance == null) {
            synchronized (DateUtils.class) {
                if (instance == null) {
                    instance = new DateUtils();
                }
            }
        }
        return instance;
    }

}
