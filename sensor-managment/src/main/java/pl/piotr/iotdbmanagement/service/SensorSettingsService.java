package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettingsRepository;

import java.util.logging.Logger;

@Service
public class SensorSettingsService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorSettingsRepository sensorSettingsRepository;

    @Autowired
    public SensorSettingsService(SensorSettingsRepository sensorSettingsRepository) {
        this.sensorSettingsRepository = sensorSettingsRepository;
    }

    public int getDefaultSensorTimeout() {
        if (sensorSettingsRepository.findByName("default").isPresent()) {
            return sensorSettingsRepository.findByName("default").get().getRequestTimeout();
        } else {
            logger.warning("Can not find default settings for sensors, timeout set as 5000ms");
            return 5000;
        }
    }

}
