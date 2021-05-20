package pl.piotr.iotdbmanagement.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.service.MeasurementService;
import pl.piotr.iotdbmanagement.service.MeasurementTypeService;
import pl.piotr.iotdbmanagement.service.SensorService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class JobsCaller {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static final int MAX_THREAD_NUMBER = 3;
    private ExecutorService executor;
    private SensorService sensorService;
    private MeasurementService measurementService;
    private MeasurementTypeService measurementTypeService;

    @Autowired
    public JobsCaller(SensorService sensorService, MeasurementService measurementService, MeasurementTypeService measurementTypeService) {
        this.sensorService = sensorService;
        this.measurementService = measurementService;
        this.measurementTypeService = measurementTypeService;
        executor = Executors.newFixedThreadPool(MAX_THREAD_NUMBER);
    }

    private void call(MeasurementsFrequency measurementsFrequency) {
        logger.info("Job for: " + measurementsFrequency + " has been started");
        List<Sensor> sensors = sensorService.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, true);
        logger.info("Found " + sensors.size() + " elements");
        sensors.forEach(sensor -> executor.submit(new Job(sensor, measurementService, sensorService, measurementTypeService)));
    }

    @Async
    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerMinute() {
        call(MeasurementsFrequency.ONCE_PER_MINUTE);
    }

}
