package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.repository.MeasurementRepository;
import pl.piotr.iotdbmanagement.repository.PlaceRepository;
import pl.piotr.iotdbmanagement.repository.SensorRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SensorService {

    private SensorRepository sensorRepository;

    private PlaceRepository placeRepository;

    private MeasurementRepository measurementRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, PlaceRepository placeRepository, MeasurementRepository measurementRepository) {
        this.sensorRepository = sensorRepository;
        this.placeRepository = placeRepository;
        this.measurementRepository = measurementRepository;
    }

    public Optional<Sensor> find(Long id) {
        return sensorRepository.findById(id);
    }

    public Optional<Sensor> findBySocket(String socket) {
        return sensorRepository.findBySocket(socket);
    }

    public List<Sensor> findAllByMeasurementsFrequencyAndIsActive(MeasurementsFrequency measurementsFrequency, Boolean activeState) {
        return sensorRepository.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, activeState);
    }

    public List<Sensor> findAndFilterAll(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency, Boolean isActive, Integer limit, Integer page) {
        List<Sensor> result;
        if (measurementType != null && measurementsFrequency != null) {
            result = sensorRepository.findAllByMeasurementTypeAndMeasurementsFrequency(measurementType, measurementsFrequency);
        } else if ((measurementType == null && measurementsFrequency != null) || (measurementType != null && measurementsFrequency == null)) {
            result = sensorRepository.findAllByMeasurementTypeOrMeasurementsFrequency(measurementType, measurementsFrequency);
        } else {
            result = sensorRepository.findAll();
        }

        if (isActive != null) {
            result = result.stream().filter(sensor -> sensor.getIsActive() == isActive).collect(Collectors.toList());
        }

        result.sort((o1, o2) -> {
            if (o1.getLastMeasurment() != null && o2.getLastMeasurment() != null) {
                return o1.getLastMeasurment().compareTo(o2.getLastMeasurment());
            } else {
                return 1;
            }
        });

        return getPageInLimit(result, limit, page);
    }

    private List<Sensor> getPageInLimit(List<Sensor> list, Integer limit, Integer page) {
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

    public Place findPlace(Long id) {
        return placeRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Transactional
    public Sensor create(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Transactional
    public void delete(Long id) {
        sensorRepository.deleteById(id);
        measurementRepository.deleteAllByPlaceIsNullAndSensorIsNull();
    }

}