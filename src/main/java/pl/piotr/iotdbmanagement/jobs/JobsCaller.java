package pl.piotr.iotdbmanagement.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.service.MeasurmentService;
import pl.piotr.iotdbmanagement.service.SensorService;

import java.time.LocalDateTime;
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
    private MeasurmentService measurmentService;

    @Autowired
    public JobsCaller(SensorService sensorService, MeasurmentService measurmentService) {
        this.sensorService = sensorService;
        this.measurmentService = measurmentService;
        executor = Executors.newFixedThreadPool(MAX_THREAD_NUMBER);
    }

    private void job(MeasurementsFrequency measurementsFrequency) {
        logger.info("Job for: " + measurementsFrequency + " has been started");
        List<Sensor> sensors = sensorService.findAllByMeasurementsFrequency(measurementsFrequency);
        sensors.forEach(sensor -> executor.submit(new Job(sensor, measurmentService)));
    }

    @Async
    @Scheduled(cron = "0/15 * * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerMinute() {
        job(MeasurementsFrequency.ONCE_PER_MINUTE);
    }

}
