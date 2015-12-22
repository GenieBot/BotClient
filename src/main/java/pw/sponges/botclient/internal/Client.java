package pw.sponges.botclient.internal;

import javax.net.ssl.SSLException;

public interface Client {
    
    void start() throws SSLException, InterruptedException;

    void stop() throws InterruptedException;

    void write(String message);

}
