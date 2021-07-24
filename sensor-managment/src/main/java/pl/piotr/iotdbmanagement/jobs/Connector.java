package pl.piotr.iotdbmanagement.jobs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class Connector {
    private final Logger LOGGER;
    private final String HOST;
    private final int PORT;
    private final int TIMEOUT;
    private boolean isConnectionFail;

    protected Connector(String host, int port, int timeout) {
        this.LOGGER = Logger.getLogger(String.format("%s %s:%d", this.getClass().getSimpleName(), host, port));
        this.HOST = host;
        this.PORT = port;
        this.TIMEOUT = timeout;
    }

    protected String connect() {
        String response = null;
        try {
            Socket socket = new Socket(HOST, PORT);
            socket.setSoTimeout(TIMEOUT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            reader.close();
            isConnectionFail = false;
            LOGGER.info("Received data: " + response);
        } catch (Exception e) {
            LOGGER.warning("Can not received data from sensor. Cause: " + e.getMessage());
            isConnectionFail = true;
        }
        return response;
    }

    public boolean connectionFailed() {
        return isConnectionFail;
    }

}
