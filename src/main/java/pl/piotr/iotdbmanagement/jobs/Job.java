package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurmentTemperatureResponse;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class Job implements Runnable {

    private Logger logger;
    private MeasurementType measurementType;
    private String address;
    private Integer port;

    protected Job(Sensor sensor) {
        logger = Logger.getLogger("sensor at: " + sensor.getSocket());
        this.measurementType = sensor.getMeasurementType();
        this.address = sensor.getAddress();
        this.port = sensor.getPort();
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            logger.info("Trying connect to: " + address + ":" + port);
            Socket socket = new Socket(address, port);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseServer = reader.readLine();
            logger.info("received data: " + responseServer);
            reader.close();

            MeasurmentTemperatureResponse response = new Gson().fromJson(responseServer, MeasurmentTemperatureResponse.class);
            logger.info("converted data: " + response.getSensor() + ", " + response.getTemperature());
        } catch (IOException e) {
            logger.warning("Can not received data from: " + address + ":" + port);
        }
    }

}
