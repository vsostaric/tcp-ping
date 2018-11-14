package tcp.test;

import tcp.factory.CommunicatorFactory;

public abstract class TPCCommunicatorTest {

    protected CommunicatorFactory factory = CommunicatorFactory.getInstance();

    protected static final int ARGS_TEST_PORT = 9900;

    protected static final int ARGS_TEST_MPS = 30;

    protected static final int ARGS_TEST_SIZE = 1000;

    protected static final String ARGS_TEST_HOSTNAME = "kompB";

    protected static final String ARGS_TEST_BIND = "192.168.0.1";

    protected static final Integer ARGS_TEST_POOL_SIZE = 25;

    protected static final String[] ARGS_PITCHER = {
            "-p",
            "-port", String.valueOf(ARGS_TEST_PORT),
            "-mps", String.valueOf(ARGS_TEST_MPS),
            "-size", String.valueOf(ARGS_TEST_SIZE),
            "-hostname", ARGS_TEST_HOSTNAME
    };

    protected static final String[] ARGS_PITCHER_WITH_POOL_SIZE = {
            "-p",
            "-port", String.valueOf(ARGS_TEST_PORT),
            "-mps", String.valueOf(ARGS_TEST_MPS),
            "-size", String.valueOf(ARGS_TEST_SIZE),
            "-hostname", ARGS_TEST_HOSTNAME,
            "-pool", String.valueOf(ARGS_TEST_POOL_SIZE)
    };

    protected static final String[] ARGS_PITCHER_LOCALHOST = {
            "-p",
            "-port", "8080",
            "-mps", "30",
            "-size", "1000",
            "-hostname", "localhost"
    };

    protected static final String[] ARGS_PITCHER_INVALID = {
            "-p",
            "-mps", "30",
            "-size", "1000"
    };

    protected static final String[] ARGS_CATCHER = {
            "-c",
            "-bind", ARGS_TEST_BIND,
            "-port", String.valueOf(ARGS_TEST_PORT)
    };

    protected static final String[] ARGS_CATCHER_LOCALHOST = {
            "-c",
            "-bind", "localhost",
            "-port", "8080"
    };

    protected static final String[] ARGS_CATCHER_INVALID = {
            "-c",
            "-port", "1120"
    };

}
