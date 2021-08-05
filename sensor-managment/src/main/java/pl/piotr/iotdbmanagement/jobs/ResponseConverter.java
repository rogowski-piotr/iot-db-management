package pl.piotr.iotdbmanagement.jobs;

import com.google.gson.Gson;
import pl.piotr.iotdbmanagement.jobs.dto.SoilMoistureResponse;
import pl.piotr.iotdbmanagement.jobs.dto.TemperatureAndHumidityResponse;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.MeasurementExecutionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ResponseConverter {
    private Logger logger;
    private String sensorResponse;
    private MeasurementExecutionService measurementExecutionService;
    private LocalDateTime dateTime;
    private Sensor sensor;
    private boolean isConversionFailed = false;

    protected ResponseConverter(String sensorResponse, MeasurementExecutionService measurementExecutionService, LocalDateTime dateTime, Sensor sensor) {
        this.logger = Logger.getLogger(String.format("sensor at: %s - %s", sensor.getSocket(), this.getClass().getSimpleName()));
        this.sensorResponse = sensorResponse;
        this.measurementExecutionService = measurementExecutionService;
        this.dateTime = dateTime;
        this.sensor = sensor;
    }

    protected boolean isFailed() {
        return isConversionFailed;
    }

    protected List<Measurement> convert() {
        switch (sensor.getMeasurementType().getType()) {
            case "TEMPERATURE_AND_HUMIDITY":
                logger.info("Type classified as TEMPERATURE_AND_HUMIDITY");
                return convertTemperatureAndHumidity();

            case "SOIL_MOISTURE":
                logger.info("Type classified as SOIL_MOISTURE");
                return convertSoilMoisture();

            default:
                logger.info("Type not classified");
                isConversionFailed = true;
                return null;
        }
    }

    private List<Measurement> convertTemperatureAndHumidity() {
        TemperatureAndHumidityResponse responseObj = new Gson().fromJson(sensorResponse, TemperatureAndHumidityResponse.class);
        if (! responseObj.getActive()) {
            logger.info("Data from sensor has an activity flag set to false");
            isConversionFailed = true;
            return null;
        }
        return List.of(
                TemperatureAndHumidityResponse.dtoToEntityTemperatureMapper()
                        .apply(responseObj, sensor, measurementExecutionService.getMeasurementTypeByString("TEMPERATURE"), dateTime),
                TemperatureAndHumidityResponse.dtoToEntityHumidityMapper()
                        .apply(responseObj, sensor, measurementExecutionService.getMeasurementTypeByString("HUMIDITY"), dateTime)
        );
    }

    private List<Measurement> convertSoilMoisture() {
        SoilMoistureResponse responseObj = new Gson().fromJson(sensorResponse, SoilMoistureResponse.class);
        if (! responseObj.getActive()) {
            logger.info("Data from sensor has an activity flag set to false");
            isConversionFailed = true;
            return null;
        }
        return List.of(
                SoilMoistureResponse.dtoToEntitySoilMoistureMapper().apply(responseObj, sensor, dateTime));
    }

}
