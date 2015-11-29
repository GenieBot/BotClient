package pw.sponges.botclient.newinternal;

import javax.net.ssl.SSLException;

public interface Client {
    
    void start() throws SSLException, InterruptedException;

    void stop();

    void write(String message);

}
