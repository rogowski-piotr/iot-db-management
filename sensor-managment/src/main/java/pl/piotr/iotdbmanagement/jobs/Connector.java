package pl.piotr.iotdbmanagement.jobs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class Connector {
    private final Logger logger;
    private final String host;
    private final int port;
    private final int timeout;
    private boolean isConnectionFail;

    protected Connector(String host, int port, int timeout) {
        this.logger = Logger.getLogger(String.format("sensor at: %s:%d - %s", host, port, this.getClass().getSimpleName()));
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    protected String connect() {
        String response = null;
        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(timeout);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            reader.close();
            isConnectionFail = false;
            logger.info("Received data: " + response);
        } catch (Exception e) {
            logger.warning("Can not received data from sensor. Cause: " + e.getMessage());
            isConnectionFail = true;
        }
        return response;
    }

    protected boolean isFailed() {
        return isConnectionFail;
    }

}
