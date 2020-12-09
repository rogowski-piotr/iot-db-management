package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import lombok.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piotr.iotdbmanagement.dto.MeasurmentTemperatureResponse;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.service.MeasurmentService;
import pl.piotr.iotdbmanagement.service.SensorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class JobDHT11Inside {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String HOST = "192.168.0.19";

    private static final int PORT = 50007;

    private SensorService sensorService;

    private MeasurmentService measurmentService;

    public JobDHT11Inside(SensorService sensorService, MeasurmentService measurmentService) {
        this.sensorService = sensorService;
        this.measurmentService = measurmentService;
    }

    @Async
    @Scheduled(cron = "0/30 * * * * ?", zone = "Europe/Warsaw")
    public void doMeasurement() {
        try {
            logger.info("Trying connect to: " + HOST + ":" + PORT);
            Socket socket = new Socket(HOST, PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseServer = reader.readLine();
            logger.info("received data: " + responseServer);
            reader.close();

            MeasurmentTemperatureResponse response = new Gson().fromJson(responseServer, MeasurmentTemperatureResponse.class);
            logger.info("converted data: " + response.getSensor() + ", " + response.getTemperature());
        } catch (IOException e) {
            logger.warning("Can not received data from: " + HOST + ":" + PORT);
        }
    }

}
