package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettingsRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<SensorSettings> findOne(Long id) {
        return sensorSettingsRepository.findById(id);
    }

    public Optional<SensorSettings> findByIdOrElseGetDefault(Long id) {
        Optional<SensorSettings> foundById = sensorSettingsRepository.findById(id);
        return foundById.isPresent() ? foundById : sensorSettingsRepository.findByName("default");
    }

    @Transactional
    public SensorSettings update(SensorSettings sensorSettings) {
        return sensorSettingsRepository.save(sensorSettings);
    }

    @Transactional
    public SensorSettings create(SensorSettings sensorSettings) {
        return sensorSettingsRepository.save(sensorSettings);
    }

    @Transactional
    public void delete(Long id) {
        sensorSettingsRepository.deleteById(id);
    }

}