package pl.piotr.iotdbmanagement.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.service.SensorService;

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


    @Autowired
    public JobsCaller(SensorService sensorService) {
        this.sensorService = sensorService;
        executor = Executors.newFixedThreadPool(MAX_THREAD_NUMBER);
    }

    @Async
    @Scheduled(cron = "0/15 * * * * ?", zone = "Europe/Warsaw")
    public void jobOncePerMinute() {
        logger.info("Job for one per minute has been started");
        sensorService.findAllByMeasurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .forEach(sensor -> executor.submit(new Job(sensor)));
    }

}
