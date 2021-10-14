package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettingsRepository;

import java.util.List;

@Service
public class SensorSettingsService extends BaseService<SensorSettings> {
    private SensorSettingsRepository sensorSettingsRepository;

    @Autowired
    public SensorSettingsService(SensorSettingsRepository sensorSettingsRepository) {
        this.sensorSettingsRepository = sensorSettingsRepository;
    }

    public List<SensorSettings> findAll(Integer limit, Integer page) {
        return getPageInLimit(sensorSettingsRepository.findAll(), limit, page);
    }

}
