package pl.piotr.iotdbmanagement.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.service.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class JobsCaller {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private static final int MAX_THREAD_NUMBER = 3;
    private ExecutorService executor;
    private MeasurementExecutionService measurementExecutionService;

    @Autowired
    public JobsCaller(MeasurementExecutionService measurementExecutionService) {
        this.measurementExecutionService = measurementExecutionService;
        executor = Executors.newFixedThreadPool(MAX_THREAD_NUMBER);
    }

    private void call(MeasurementsFrequency measurementsFrequency) {
        logger.info("Job for: " + measurementsFrequency + " has been started");
        List<Sensor> sensors = measurementExecutionService.findSensorsToMeasure(measurementsFrequency);
        sensors.forEach(
                sensor -> executor.submit(new Job(sensor, measurementExecutionService))
        );
    }

    @Async
    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerMinute() {
        call(MeasurementsFrequency.ONCE_PER_MINUTE);
    }

    @Async
    @Scheduled(cron = "0 0/5 * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerFiveMinutes() {
        call(MeasurementsFrequency.ONCE_PER_FIVE_MINUTES);
    }

    @Async
    @Scheduled(cron = "0 0/15 * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerFifteenMinutes() {
        call(MeasurementsFrequency.ONCE_PER_FIFTEEN_MINUTES);
    }

    @Async
    @Scheduled(cron = "0 0/30 * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerThirtyMinutes() {
        call(MeasurementsFrequency.ONCE_PER_THIRTY_MINUTES);
    }

    @Async
    @Scheduled(cron = "0 0 0/1 * * ?", zone = "Europe/Warsaw")
    public void jobOncePerHour() {
        call(MeasurementsFrequency.ONCE_PER_HOUR);
    }

    @Async
    @Scheduled(cron = "0 0 0/3 * * ?", zone = "Europe/Warsaw")
    public void jobOncePerThreeHours() {
        call(MeasurementsFrequency.ONCE_PER_THREE_HOURS);
    }

    @Async
    @Scheduled(cron = "0 0 0/12 * * ?", zone = "Europe/Warsaw")
    public void jobOncePerTwelveHours() {
        call(MeasurementsFrequency.ONCE_PER_TWELVE_HOURS);
    }

    /*@Async
    @Scheduled(cron = "0 0 0 0/1 * ?", zone = "Europe/Warsaw")
    public void jobOncePerDay() {
        call(MeasurementsFrequency.ONCE_PER_DAY);
    }*/

}
