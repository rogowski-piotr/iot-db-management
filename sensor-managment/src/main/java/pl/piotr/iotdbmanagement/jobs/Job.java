package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurmentTemperatureAndHumidityResponse;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.MeasurementService;
import pl.piotr.iotdbmanagement.service.SensorService;

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
    private MeasurementService measurementService;
    private SensorService sensorService;

    protected Job(Sensor sensor, MeasurementService measurementService, SensorService sensorService) {
        logger = Logger.getLogger("sensor at: " + sensor.getSocket());
        this.sensor = sensor;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
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
            sensor.setIsActive(false);
            sensorService.update(sensor);
            return;
        }
        logger.info("received data: " + response);

        List<Measurement> measurements = new ArrayList<>();

        Measurement infoObject = Measurement.builder()
                .date(LocalDateTime.now())
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();

        switch (sensor.getMeasurementType()) {
            case TEMPERATURE_AND_HUMIDITY:
                MeasurmentTemperatureAndHumidityResponse responseObject = new Gson()
                        .fromJson(response, MeasurmentTemperatureAndHumidityResponse.class);
                if ((boolean)sensor.getIsActive() != (boolean)responseObject.getActive()) {
                    sensor.setIsActive(responseObject.getActive());
                    sensorService.update(sensor);
                    if (!sensor.getIsActive()) return;
                }
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse
                                .dtoToEntityTemperatureMapper().apply(responseObject, infoObject));
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse
                                .dtoToEntityHumidityMapper().apply(responseObject, infoObject));
                break;

            case TEMPERATURE:
                break;

            case HUMIDITY:
                break;

            default:
                return;
        }

        measurements
                .forEach(measurment -> measurementService.create(measurment));

        logger.info("data has been inserted");
    }

}
