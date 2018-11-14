package tcp.domain;

import org.junit.Assert;
import org.junit.Test;
import tcp.factory.Args;
import tcp.test.TPCCommunicatorTest;

public class ArgsTest extends TPCCommunicatorTest {

    private Args args;

    @Test
    public void testCreatePitcher() {

        args = new Args(ARGS_PITCHER);

        Assert.assertTrue(args.isPitcher());
        Assert.assertFalse(args.isCatcher());
        Assert.assertTrue(args.getPort() == ARGS_TEST_PORT);
        Assert.assertTrue(args.getMps() == ARGS_TEST_MPS);
        Assert.assertTrue(args.getSize() == ARGS_TEST_SIZE);
        Assert.assertSame(args.getHostname(), ARGS_TEST_HOSTNAME);
        Assert.assertTrue(args.getPitcherPoolSize() == null);

    }

    @Test
    public void testCreateCatcher() {

        args = new Args(ARGS_CATCHER);

        Assert.assertTrue(args.isCatcher());
        Assert.assertFalse(args.isPitcher());
        Assert.assertTrue(args.getPort() == ARGS_TEST_PORT);
        Assert.assertSame(args.getBind(), ARGS_TEST_BIND);

    }

    @Test
    public void testCreatePitcherWithPoolSize() {

        args = new Args(ARGS_PITCHER_WITH_POOL_SIZE);

        Assert.assertTrue(args.getPort() == ARGS_TEST_PORT);
        Assert.assertTrue(args.getMps() == ARGS_TEST_MPS);
        Assert.assertTrue(args.getSize() == ARGS_TEST_SIZE);
        Assert.assertSame(args.getHostname(), ARGS_TEST_HOSTNAME);
        Assert.assertTrue(args.getPitcherPoolSize() == ARGS_TEST_POOL_SIZE);

    }

}
