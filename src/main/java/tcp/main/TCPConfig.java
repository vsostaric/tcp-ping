package tcp.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TCPConfig {

    private static final Properties props;

    static {
        props = new Properties();
        try (InputStream input = TCPConfig.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String MIN_MESSAGE_SIZE = "message.size.min";

    public static final String MAX_MESSAGE_SIZE = "message.size.max";

    public static final String DEFAULT_MESSAGE_SIZE = "message.size.default";

    public static final String MESSAGE_DATE_TIME_PATTERN = "message.date.time.pattern";

    public static final String MESSAGE_DELIMITER = "message.delimiter";

    public static final String MIN_MESSAGES_FOR_SPEED_UPDATE = "message.speed.update";

    public static final String MESSAGE_ID_LENGTH = "message.id.length";

    public static final String MESSAGE_POOL_SIZE_DEFAULT = "message.pool.size.default";

    public static final String MESSAGE_WAIT_FOR_REPLY = "message.wait.for.reply";

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

}
