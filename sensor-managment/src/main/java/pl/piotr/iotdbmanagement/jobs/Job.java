package pl.piotr.iotdbmanagement.jobs;

import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.*;

import java.time.LocalDateTime;
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
            List<Measurement> measurements = convertResponseToObject(response);
            measurements.forEach(measurement -> measurementExecutionService.addMeasurement(measurement));
            measurementExecutionService.verifyToActivate(sensor);
            logger.info("data has been inserted");
        } catch (InterruptedException e) {
            logger.warning("Thread has been interrupted!");
        }
    }

    private void deactivateAndInterrupt() throws InterruptedException {
        if (measurementExecutionService.verifyToDeactivate(sensor)) {
            logger.info("aborting and deactivating sensor");
        } else {
            logger.info("aborting but sensor still active");
        }
        throw new InterruptedException();
    }

    private String connectWithSensor() throws InterruptedException {
        Connector connector = new Connector(sensor.getAddress(), sensor.getPort(), REQUEST_TIMEOUT);
        String response = connector.connect();
        if (connector.isFailed()) {
            deactivateAndInterrupt();
        }
        return response;
    }

    private List<Measurement> convertResponseToObject(String response) throws InterruptedException {
        ResponseConverter converter = new ResponseConverter(response, measurementExecutionService, LocalDateTime.now(), sensor);
        List<Measurement> convertedMeasurements = converter.convert();
        if (converter.isFailed()) {
            deactivateAndInterrupt();
        }
        return convertedMeasurements;
    }

}
