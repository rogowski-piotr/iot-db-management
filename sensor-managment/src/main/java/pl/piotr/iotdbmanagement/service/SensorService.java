package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;

import java.util.List;

@Service
public class SensorService {

    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAllByMeasurementsFrequencyAndIsActive(MeasurementsFrequency measurementsFrequency, Boolean activeState) {
        return sensorRepository.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, activeState);
    }

    @Transactional
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Transactional
    public Sensor create(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

}
