package tcp.domain.communicators;

import tcp.factory.Args;
import tcp.frame.FrameController;

import java.io.IOException;
import java.net.ServerSocket;

public final class Catcher extends AbstractCommunicator {

    private static Catcher instance;

    private String bind;

    private Integer port;

    private Integer messagesCaught;

    @Override
    public void communicate() throws IOException {

        super.communicate();

        try (ServerSocket listener = new ServerSocket(port)) {
            while (true) {
                messagesCaught = messagesCaught + 1;
                new CatcherResponder(listener.accept()).respond();
            }
        }
    }

    @Override
    protected void openFrame() {
        FrameController.getInstance().openFrame("Catcher Frame", "Catching on port " + port, 50);
    }

    @Override
    protected void updateFrame() {
        FrameController.getInstance().updateFrame("Messages caught: " + messagesCaught, 20);
    }

    public static Catcher getInstance(Args args) {
        if (instance == null) {
            synchronized (Catcher.class) {
                if (instance == null) {
                    instance = new Catcher(args);
                }
            }
        }
        return instance;
    }

    public static void dumpInstance() {
        instance = null;
    }

    private Catcher(Args args) {
        super();
        this.bind = args.getBind();
        this.port = args.getPort();
        messagesCaught = 0;
    }

    public String getBind() {
        return bind;
    }

    public Integer getPort() {
        return port;
    }

}
