package tcp.domain.communicators;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCommunicator implements Communicator {

    @Override
    public void communicate() throws IOException {

        openFrame();

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            updateFrame();
        }, 1, 1, TimeUnit.SECONDS);

    }

    protected abstract void openFrame();

    protected abstract void updateFrame();

}
