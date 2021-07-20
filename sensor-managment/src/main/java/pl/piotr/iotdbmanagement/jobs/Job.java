package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurementSoilMoistureResponse;
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

        switch (sensor.getMeasurementType().getType()) {
            case "TEMPERATURE_AND_HUMIDITY":
                logger.info("type classified as TEMPERATURE_AND_HUMIDITY");
                MeasurmentTemperatureAndHumidityResponse responseTempHumi = new Gson()
                        .fromJson(response, MeasurmentTemperatureAndHumidityResponse.class);
                if (! responseTempHumi.getActive()) {
                    sensor.setIsActive(false);
                    sensorService.update(sensor);
                    return;
                }
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse.dtoToEntityTemperatureMapper()
                                .apply(responseTempHumi, sensor, measurementTypeService.getTypeOfString("TEMPERATURE"), LocalDateTime.now())
                );
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse.dtoToEntityHumidityMapper()
                                .apply(responseTempHumi, sensor, measurementTypeService.getTypeOfString("HUMIDITY"), LocalDateTime.now())
                );
                break;

            case "SOIL_MOISTURE":
                logger.info("type classified as SOIL_MOISTURE");
                MeasurementSoilMoistureResponse responseSoilMoisture = new Gson()
                        .fromJson(response, MeasurementSoilMoistureResponse.class);
                if (! responseSoilMoisture.getActive()) {
                    sensor.setIsActive(false);
                    sensorService.update(sensor);
                    return;
                }
                measurements.add(
                        MeasurementSoilMoistureResponse.dtoToEntitySoilMoistureMapper()
                                .apply(responseSoilMoisture, sensor, LocalDateTime.now())
                );
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
