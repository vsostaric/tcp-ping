package tcp.domain;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static tcp.main.TCPConfig.*;

public class MessageTest {

    private static Integer maxMessageSize = Integer.valueOf(getProperty(MAX_MESSAGE_SIZE));

    private static Integer minMessageSize = Integer.valueOf(getProperty(MIN_MESSAGE_SIZE));

    private static Integer messageIdLength = Integer.valueOf(getProperty(MESSAGE_ID_LENGTH));

    @Test
    public void testMessageCreation() {

        Message message;
        int[] testSizes = {
                minMessageSize,
                (maxMessageSize - minMessageSize) / 2,
                maxMessageSize
        };

        for (int size : testSizes) {
            message = new Message(size, LocalDateTime.now());

            Assert.assertNotNull(message.getId());
            Assert.assertTrue(message.getId().length() == messageIdLength);

            Assert.assertTrue(message.getMessageContent().length() == size);
        }

    }

}
