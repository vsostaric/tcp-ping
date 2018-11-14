package tcp.domain.communicators;

import org.junit.Ignore;
import org.junit.Test;
import tcp.factory.Args;
import tcp.test.TPCCommunicatorTest;

import java.io.IOException;

@Ignore
public class PitcherTest extends TPCCommunicatorTest {

    @Test
    public void testConnection() throws IOException {

        Pitcher.dumpInstance();
        Pitcher pitcher = (Pitcher) factory.getCommunicator(new Args(ARGS_PITCHER_LOCALHOST));

        pitcher.communicate();

    }

}
