package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurementSoilMoistureResponse;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.jobs.dto.MeasurmentTemperatureAndHumidityResponse;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Job implements Runnable {
    private final int REQUEST_TIMEOUT;
    private Logger logger;
    private Sensor sensor;
    private MeasurementExecutionService measurementExecutionService;

    protected Job(Sensor sensor, MeasurementExecutionService measurementExecutionService) {
        logger = Logger.getLogger("sensor at: " + sensor.getSocket());
        this.REQUEST_TIMEOUT = sensor.getSensorSettings() != null ? sensor.getSensorSettings().getRequestTimeout() : measurementExecutionService.getDefaultSensorTimeout();
        this.sensor = sensor;
        this.measurementExecutionService = measurementExecutionService;
    }

    @Override
    public void run() {
        try {
            String response = connectWithSensor();
            List<Measurement> measurements = transformResponseToObject(response);
            measurements.forEach(measurement -> measurementExecutionService.addMeasurement(measurement));
            logger.info("data has been inserted");
        } catch (InterruptedException e) {
            logger.warning("Thread has been interrupted!");
        }
    }

    private void deactivateAndInterrupt() throws InterruptedException {
        logger.info("deactivating and aborting");
        measurementExecutionService.deactivateSensor(sensor);
        throw new InterruptedException();
    }

    private String connectWithSensor() throws InterruptedException {
        String response = null;
        try {
            Socket socket = new Socket(sensor.getAddress(), sensor.getPort());
            socket.setSoTimeout(REQUEST_TIMEOUT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            reader.close();
        } catch (Exception e) {
            logger.warning("Can not received data from sensor. Cause: " + e.getMessage());
            deactivateAndInterrupt();
        }
        logger.info("Received data: " + response);
        return response;
    }

    private List<Measurement> transformResponseToObject(String response) throws InterruptedException {
        List<Measurement> measurements = new ArrayList<>();

        switch (sensor.getMeasurementType().getType()) {
            case "TEMPERATURE_AND_HUMIDITY":
                logger.info("Type classified as TEMPERATURE_AND_HUMIDITY");
                MeasurmentTemperatureAndHumidityResponse responseTempHumi = new Gson()
                        .fromJson(response, MeasurmentTemperatureAndHumidityResponse.class);
                if (! responseTempHumi.getActive()) {
                    logger.info("Data from sensor has an activity flag set to false");
                    deactivateAndInterrupt();
                }
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse.dtoToEntityTemperatureMapper()
                                .apply(responseTempHumi, sensor, measurementExecutionService.getMeasurementTypeByString("TEMPERATURE"), LocalDateTime.now())
                );
                measurements.add(
                        MeasurmentTemperatureAndHumidityResponse.dtoToEntityHumidityMapper()
                                .apply(responseTempHumi, sensor, measurementExecutionService.getMeasurementTypeByString("HUMIDITY"), LocalDateTime.now())
                );
                break;

            case "SOIL_MOISTURE":
                logger.info("Type classified as SOIL_MOISTURE");
                MeasurementSoilMoistureResponse responseSoilMoisture = new Gson()
                        .fromJson(response, MeasurementSoilMoistureResponse.class);
                if (! responseSoilMoisture.getActive()) {
                    logger.info("Data from sensor has an activity flag set to false");
                    deactivateAndInterrupt();
                }
                measurements.add(
                        MeasurementSoilMoistureResponse.dtoToEntitySoilMoistureMapper()
                                .apply(responseSoilMoisture, sensor, LocalDateTime.now())
                );
                break;

            default:
                logger.info("Type not classified");
                deactivateAndInterrupt();
        }
        return measurements;
    }

}
