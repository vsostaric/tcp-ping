package tcp.factory;

import tcp.domain.communicators.Communicator;
import tcp.domain.communicators.Catcher;
import tcp.domain.communicators.Pitcher;

public final class CommunicatorFactory {

    private static CommunicatorFactory instance;

    public Communicator getCommunicator(Args args) {

        Communicator communicator = null;

        if (args.isCatcher()) {
            communicator = Catcher.getInstance(args);
        } else if (args.isPitcher()) {
            communicator = Pitcher.getInstance(args);
        }

        return communicator;
    }

    public static CommunicatorFactory getInstance() {
        if (instance == null) {
            synchronized (CommunicatorFactory.class) {
                if (instance == null) {
                    instance = new CommunicatorFactory();
                }
            }
        }
        return instance;
    }

}
