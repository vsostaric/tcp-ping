package tcp.main;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import static tcp.main.TCPConfig.*;

public class TCPConfigTest {

    @Test
    public void testPropertyLoad() {

        Assert.assertTrue(!StringUtils.isEmpty(getProperty(MIN_MESSAGE_SIZE)));
        Assert.assertTrue(!StringUtils.isEmpty(getProperty(MAX_MESSAGE_SIZE)));
        Assert.assertTrue(!StringUtils.isEmpty(getProperty(DEFAULT_MESSAGE_SIZE)));
        Assert.assertTrue(!StringUtils.isEmpty(getProperty(MESSAGE_DATE_TIME_PATTERN)));
        Assert.assertTrue(!StringUtils.isEmpty(getProperty(MESSAGE_DELIMITER)));
        Assert.assertTrue(!StringUtils.isEmpty(getProperty(MIN_MESSAGES_FOR_SPEED_UPDATE)));
        Assert.assertTrue(!StringUtils.isEmpty(getProperty(MESSAGE_ID_LENGTH)));

    }

}
