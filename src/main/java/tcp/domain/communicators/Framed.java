package tcp.domain.communicators;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public interface Framed {

    void openFrame();

    void updateFrame();

    default void startFrame() throws IOException {

        openFrame();

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            updateFrame();
        }, 1, 1, TimeUnit.SECONDS);

    }

}
