package tcp.domain.communicators;

import org.junit.Ignore;
import org.junit.Test;
import tcp.factory.Args;
import tcp.test.TPCCommunicatorTest;

import java.io.IOException;

@Ignore
public class CatcherTest extends TPCCommunicatorTest {

    @Test
    public void testConnection() throws IOException {

        Catcher.dumpInstance();
        Catcher catcher = (Catcher) factory.getCommunicator(new Args(ARGS_CATCHER_LOCALHOST));

        catcher.communicate();

    }


}
