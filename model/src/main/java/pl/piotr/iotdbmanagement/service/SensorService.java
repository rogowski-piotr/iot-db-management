package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.repository.PlaceRepository;
import pl.piotr.iotdbmanagement.repository.SensorRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SensorService {
    private SensorRepository sensorRepository;
    private PlaceRepository placeRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> find(Long id) {
        return sensorRepository.findById(id);
    }

    public List<Sensor> findAllByMeasurementsFrequency(MeasurementsFrequency measurementsFrequency) {
        return sensorRepository.findAllByMeasurementsFrequency(measurementsFrequency);
    }

    public List<Sensor> findAllByMeasurementsFrequencyAndIsActive(MeasurementsFrequency measurementsFrequency, Boolean activeState) {
        return sensorRepository.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, activeState);
    }

    public Optional<Place> findPlaceBySensor(Sensor sensor) {
        return placeRepository.findById(sensor.getActualPosition().getId());
    }

    public List<Sensor> findAllByLastMeasurment(LocalDateTime lastMeasurment) {
        return sensorRepository.findAllByLastMeasurment(lastMeasurment);
    }

    public List<Sensor> findAllByActualPosition(Place actualPosition) {
        return sensorRepository.findAllByActualPosition(actualPosition);
    }

    public List<Sensor> findAllByMeasurementTypeAndMeasurementsFrequency(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency) {
        if (measurementType != null && measurementsFrequency == null) {
            return sensorRepository.findAllByMeasurementType(measurementType);

        } else if (measurementType == null && measurementsFrequency != null) {
            return sensorRepository.findAllByMeasurementsFrequency(measurementsFrequency);

        } else if (measurementType != null && measurementsFrequency != null) {
            return sensorRepository.findAllByMeasurementTypeAndMeasurementsFrequency(measurementType, measurementsFrequency);

        } else {
            return sensorRepository.findAll();
        }
    }

    public List<Sensor> sortByLastMeasurements(List<Sensor> sensors) {
        Collections.sort(sensors, new Comparator<Sensor>() {
            @Override
            public int compare(Sensor o1, Sensor o2) {
                return o2.getLastMeasurment().compareTo(o1.getLastMeasurment());
            }
        });
        return sensors;
    }

    public List<Sensor> getPageInLimit(List<Sensor> list, Integer limit, Integer page) {
        if (limit == null) {
            limit = 10;
        }
        if (page == null) {
            page = 0;
        }
        int indexFrom = page * limit;
        int indexTo = indexFrom + limit;
        try {
            list = list.subList(indexFrom, indexTo > list.size() ? list.size() : indexTo);
        } catch (IllegalArgumentException | NullPointerException |IndexOutOfBoundsException e) {
            list = List.of();
        }
        return list;
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
