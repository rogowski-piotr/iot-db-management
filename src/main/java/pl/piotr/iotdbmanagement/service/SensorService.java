package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.repository.SensorRepository;

import java.util.List;

@Service
public class SensorService {

    private SensorRepository repository;

    @Autowired
    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    public List<Sensor> findAll() {
        return repository.findAll();
    }

    public List<Sensor> findAllByMeasurementsFrequency(MeasurementsFrequency measurementsFrequency) {
        return repository.findAllByMeasurementsFrequency(measurementsFrequency);
    }

    public List<Sensor> findAllByLastMeasurment(MeasurmentDate lastMeasurment) {
        return repository.findAllByLastMeasurment(lastMeasurment);
    }

    public List<Sensor> findAllByActualPosition(Place actualPosition) {
        return repository.findAllByActualPosition(actualPosition);
    }

    @Transactional
    public Sensor update(Sensor sensor) {
        return repository.save(sensor);
    }

    @Transactional
    public Sensor create(Sensor sensor) {
        return repository.save(sensor);
    }

}
