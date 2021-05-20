package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurmentTemperatureAndHumidityResponse;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.MeasurementService;
import pl.piotr.iotdbmanagement.service.MeasurementTypeService;
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
    private MeasurementTypeService measurementTypeService;

    protected Job(Sensor sensor, MeasurementService measurementService, SensorService sensorService, MeasurementTypeService measurementTypeService) {
        logger = Logger.getLogger("sensor at: " + sensor.getSocket());
        this.sensor = sensor;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.measurementTypeService = measurementTypeService;
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

        logger.info("type: " + sensor.getMeasurementType().getType());

        switch (sensor.getMeasurementType().getType()) {
            case "TEMPERATURE_AND_HUMIDITY":
                logger.info("type classified as TEMPERATURE_AND_HUMIDITY");
                MeasurmentTemperatureAndHumidityResponse responseObject = new Gson()
                        .fromJson(response, MeasurmentTemperatureAndHumidityResponse.class);
                if ((boolean) sensor.getIsActive() != (boolean) responseObject.getActive()) {
                    sensor.setIsActive(responseObject.getActive());
                    sensorService.update(sensor);
                    if (!sensor.getIsActive()) return;
                }
                infoObject.setMeasurementType(measurementTypeService.getTypeOfString("TEMPERATURE"));
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse
                                .dtoToEntityTemperatureMapper().apply(responseObject, infoObject));
                infoObject.setMeasurementType(measurementTypeService.getTypeOfString("HUMIDITY"));
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse
                                .dtoToEntityHumidityMapper().apply(responseObject, infoObject));
                break;

            case "TEMPERATURE":
                logger.info("type classified as TEMPERATURE");
                break;

            case "HUMIDITY":
                logger.info("type classified as HUMIDITY");
                break;

            default:
                logger.info("type not classified");
                return;
        }

        measurements
                .forEach(measurment -> measurementService.create(measurment));

        logger.info("data has been inserted");
    }

}
