package tcp.factory;

import org.junit.Assert;
import org.junit.Test;
import tcp.domain.communicators.Catcher;
import tcp.domain.communicators.Communicator;
import tcp.domain.communicators.Pitcher;
import tcp.test.TPCCommunicatorTest;

import java.lang.reflect.Field;

import static tcp.main.TCPConfig.*;

public class CommunicatorFactoryTest extends TPCCommunicatorTest {

    private final static Integer defaultMessageSize = Integer.valueOf(getProperty(DEFAULT_MESSAGE_SIZE));

    private final static Integer defaultPoolSize = Integer.valueOf(getProperty(MESSAGE_POOL_SIZE_DEFAULT));

    @Test
    public void testCatcherCreation() {

        Catcher.dumpInstance();
        Communicator communicator = factory.getCommunicator(new Args(ARGS_CATCHER));

        Assert.assertTrue(communicator instanceof Catcher);

        Catcher catcher = (Catcher) communicator;

        Assert.assertSame(catcher.getBind(), ARGS_TEST_BIND);
        Assert.assertTrue(catcher.getPort() == ARGS_TEST_PORT);

    }

    @Test
    public void testPitcherCreation() {

        Pitcher.dumpInstance();
        Communicator communicator = factory.getCommunicator(new Args(ARGS_PITCHER));

        Assert.assertTrue(communicator instanceof Pitcher);

        Pitcher pitcher = (Pitcher) communicator;

        try {
            Field sizeField = pitcher.getClass().getDeclaredField("size");
            sizeField.setAccessible(true);
            int size = (int) sizeField.get(pitcher);
            Assert.assertTrue(size == ARGS_TEST_SIZE);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(testField(pitcher, "size", ARGS_TEST_SIZE));
        Assert.assertTrue(testField(pitcher, "port", ARGS_TEST_PORT));
        Assert.assertTrue(testField(pitcher, "messagePerSecond", ARGS_TEST_MPS));
        Assert.assertTrue(testField(pitcher, "hostname", ARGS_TEST_HOSTNAME));
        Assert.assertTrue(testField(pitcher, "poolSize", defaultPoolSize));

    }

    @Test
    public void testPitcherCreationWithPoolSize() {

        Pitcher.dumpInstance();
        Communicator communicator = factory.getCommunicator(new Args(ARGS_PITCHER_WITH_POOL_SIZE));

        Assert.assertTrue(communicator instanceof Pitcher);

        Pitcher pitcher = (Pitcher) communicator;

        Assert.assertTrue(testField(pitcher, "poolSize", ARGS_TEST_POOL_SIZE));

    }

    @Test
    public void testPitcherCreationMissingSizeEqualsDefault() {

        String[] args = {
                "-p",
                "-port", String.valueOf(ARGS_TEST_PORT),
                "-mps", String.valueOf(ARGS_TEST_MPS),
                "-hostname", ARGS_TEST_HOSTNAME
        };

        Pitcher.dumpInstance();
        Communicator communicator = factory.getCommunicator(new Args(args));

        Assert.assertTrue(communicator instanceof Pitcher);

        Pitcher pitcher = (Pitcher) communicator;

        Assert.assertTrue(testField(pitcher, "size", defaultMessageSize.intValue()));

    }

    private boolean testField(Object target, String declaredFieldName, Object expectedResult) {

        Object fieldValue;
        try {
            Field field = target.getClass().getDeclaredField(declaredFieldName);
            field.setAccessible(true);
            fieldValue = field.get(target);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }

        return expectedResult.equals(fieldValue);
    }


}
