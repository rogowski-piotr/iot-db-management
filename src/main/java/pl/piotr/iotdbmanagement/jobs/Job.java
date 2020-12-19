package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurmentTemperatureAndHumidityResponse;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.service.MeasurmentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Job implements Runnable {

    private Logger logger;
    private Sensor sensor;
    private MeasurmentService service;

    protected Job(Sensor sensor, MeasurmentService service) {
        logger = Logger.getLogger("sensor at: " + sensor.getSocket());
        this.sensor = sensor;
        this.service = service;
    }

    @Override
    public void run() {
        logger.info("Trying connect to: " + sensor.getSocket());
        String response;

        try (Socket socket = new Socket(sensor.getAddress(), sensor.getPort())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            reader.close();
        } catch (IOException | NullPointerException e) {
            logger.warning("Can not received data from: " + sensor.getSocket() + "; cause: " + e.getMessage());
            return;
        }
        logger.info("received data: " + response);

        List<Measurment> measurments = new ArrayList<>();

        switch (sensor.getMeasurementType()) {
            case TEMPERATURE_AND_HUMIDITY:
                MeasurmentTemperatureAndHumidityResponse responseObject = new Gson()
                        .fromJson(response, MeasurmentTemperatureAndHumidityResponse.class);
                measurments.add(
                        MeasurmentTemperatureAndHumidityResponse
                                .dtoToEntityTemperatureMapper().apply(responseObject));
                measurments.add(
                        MeasurmentTemperatureAndHumidityResponse
                                .dtoToEntityHumidityMapper().apply(responseObject));
                break;

            case TEMPERATURE:
                break;

            case HUMIDITY:
                break;

            default:
                return;
        }

        measurments.forEach(
                measurment -> {
                    measurment.setDate(LocalDateTime.now());
                    measurment.setSensor(sensor);
                    measurment.setPlace(sensor.getActualPosition());
                    service.create(measurment);
                });
        logger.info("data has been inserted");
    }

}
