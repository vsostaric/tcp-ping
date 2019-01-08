package tcp.factory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "-c", description = "Catcher work mode")
    private boolean catcher = false;

    @Parameter(names = "-p", description = "Pitcher work mode")
    private boolean pitcher = false;

    @Parameter(names = {"-bind", "-b"}, description = "TCP socket bind address to listen")
    private String bind;

    @Parameter(names = "-port", description = "TCP socket port to listen/connect")
    private Integer port;

    @Parameter(names = "-mps", description = "Messages per second")
    private Integer mps;

    @Parameter(names = {"-size", "-s"}, description = "Length of message")
    private Integer size;

    @Parameter(names = {"-hostname", "-host", "-h"}, description = "Hostname of Catcher")
    private String hostname;

    @Parameter(names = {"-pool", "-poolSize", "-ps"}, description = "Pitcher pool size")
    private Integer pitcherPoolSize;

    public Args(String[] args) {
        JCommander.newBuilder().addObject(this).build().parse(args);
    }

    public String getBind() {
        return bind;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getMps() {
        return mps;
    }

    public Integer getSize() {
        return size;
    }

    public String getHostname() {
        return hostname;
    }

    public Integer getPitcherPoolSize() {
        return pitcherPoolSize;
    }

    public boolean isCatcher() {
        return catcher;
    }

    public boolean isPitcher() {
        return pitcher;
    }
}
