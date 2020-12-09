package pl.piotr.iotdbmanagement.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piotr.iotdbmanagement.service.sensors.TemperatureInService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class JobDHT11Inside {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String HOST = "192.168.0.19";

    private static final int PORT = 50007;

    private TemperatureInService temperatureInService;

    @Autowired
    public JobDHT11Inside(TemperatureInService temperatureInService) {
        this.temperatureInService = temperatureInService;
    }

    @Async
    @Scheduled(cron = "0/30 * * * * ?", zone = "Europe/Warsaw")
    public void scheduleTask() {
        try {
            logger.info("Trying connect to: " + HOST + ":" + PORT);
            Socket socket = new Socket(HOST,PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseServer = reader.readLine();
            logger.info("received data: " + responseServer);

            reader.close();
        } catch (IOException e) {
            logger.warning("Can not received data from: " + HOST + ":" + PORT);
        }
    }

}
