package tcp.factory;

import org.junit.Assert;
import org.junit.Test;
import tcp.domain.validation.Status;
import tcp.domain.validation.ValidationResult;
import tcp.test.TPCCommunicatorTest;

import static tcp.main.TCPConfig.*;

public class ArgsValidatorTest extends TPCCommunicatorTest {

    private ArgsValidator validator = ArgsValidator.getInstance();

    private static Integer maxMessageSize = Integer.valueOf(getProperty(MAX_MESSAGE_SIZE));

    private static Integer minMessageSize = Integer.valueOf(getProperty(MIN_MESSAGE_SIZE));

    private ValidationResult result;

    @Test
    public void testValidCatcher() {

        result = validator.validate(new Args(ARGS_CATCHER));

        Assert.assertTrue(Status.OK.equals(result.getStatus()));

    }

    @Test
    public void testValidPitcher() {

        result = validator.validate(new Args(ARGS_PITCHER));

        Assert.assertTrue(Status.OK.equals(result.getStatus()));

    }

    @Test
    public void testInvalidCatcher() {

        result = validator.validate(new Args(ARGS_CATCHER_INVALID));

        Assert.assertTrue(Status.NOK.equals(result.getStatus()));

    }

    @Test
    public void testInvalidPitcher() {

        result = validator.validate(new Args(ARGS_PITCHER_INVALID));

        Assert.assertTrue(Status.NOK.equals(result.getStatus()));

    }

    @Test
    public void testInvalidPitcher_messageTooLongOrTooShort() {

        String[] args_size_to_long = {
                "-p",
                "-port", String.valueOf(ARGS_TEST_PORT),
                "-mps", String.valueOf(ARGS_TEST_MPS),
                "-size", String.valueOf(maxMessageSize + 10),
                "-hostname", ARGS_TEST_HOSTNAME
        };

        result = validator.validate(new Args(args_size_to_long));

        Assert.assertTrue(Status.NOK.equals(result.getStatus()));

        String[] args_size_to_short = {
                "-p",
                "-port", String.valueOf(ARGS_TEST_PORT),
                "-mps", String.valueOf(ARGS_TEST_MPS),
                "-size", String.valueOf(minMessageSize - 1),
                "-hostname", ARGS_TEST_HOSTNAME
        };

        result = validator.validate(new Args(args_size_to_short));

        Assert.assertTrue(Status.NOK.equals(result.getStatus()));

        String[] args_size_missing = {
                "-p",
                "-port", String.valueOf(ARGS_TEST_PORT),
                "-mps", String.valueOf(ARGS_TEST_MPS),
                "-hostname", ARGS_TEST_HOSTNAME
        };

        result = validator.validate(new Args(args_size_missing));

        Assert.assertTrue(Status.OK.equals(result.getStatus()));

    }

}
