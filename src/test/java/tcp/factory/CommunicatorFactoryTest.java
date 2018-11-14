package tcp.factory;

import org.junit.Assert;
import org.junit.Test;
import tcp.domain.communicators.Communicator;
import tcp.domain.communicators.Catcher;
import tcp.domain.communicators.Pitcher;
import tcp.test.TPCCommunicatorTest;

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

        Assert.assertTrue(pitcher.getSize() == ARGS_TEST_SIZE);
        Assert.assertTrue(pitcher.getPort() == ARGS_TEST_PORT);
        Assert.assertTrue(pitcher.getMessagePerSecond() == ARGS_TEST_MPS);
        Assert.assertSame(pitcher.getHostname(), ARGS_TEST_HOSTNAME);
        Assert.assertTrue(pitcher.getPoolSize() == defaultPoolSize);

    }

    @Test
    public void testPitcherCreationWithPoolSize() {

        Pitcher.dumpInstance();
        Communicator communicator = factory.getCommunicator(new Args(ARGS_PITCHER_WITH_POOL_SIZE));

        Assert.assertTrue(communicator instanceof Pitcher);

        Pitcher pitcher = (Pitcher) communicator;

        Assert.assertTrue(pitcher.getSize() == ARGS_TEST_SIZE);
        Assert.assertTrue(pitcher.getPort() == ARGS_TEST_PORT);
        Assert.assertTrue(pitcher.getMessagePerSecond() == ARGS_TEST_MPS);
        Assert.assertSame(pitcher.getHostname(), ARGS_TEST_HOSTNAME);
        Assert.assertTrue(pitcher.getPoolSize() == ARGS_TEST_POOL_SIZE);

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

        Assert.assertTrue(pitcher.getSize().equals(defaultMessageSize.intValue()));

        Assert.assertTrue(pitcher.getPort() == ARGS_TEST_PORT);
        Assert.assertTrue(pitcher.getMessagePerSecond() == ARGS_TEST_MPS);
        Assert.assertSame(pitcher.getHostname(), ARGS_TEST_HOSTNAME);

    }

}
