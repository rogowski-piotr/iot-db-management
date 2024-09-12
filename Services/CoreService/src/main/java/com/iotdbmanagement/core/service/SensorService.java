package com.iotdbmanagement.core.service;

import com.iotdbmanagement.core.enums.MeasurementsFrequency;
import com.iotdbmanagement.core.measurement.MeasurementRepository;
import com.iotdbmanagement.core.measurementtype.MeasurementType;
import com.iotdbmanagement.core.place.Place;
import com.iotdbmanagement.core.place.PlaceRepository;
import com.iotdbmanagement.core.sensor.Sensor;
import com.iotdbmanagement.core.sensor.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SensorService extends BaseService<Sensor> {
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

    public Place findPlace(Long id) {
        return id != null
                ? placeRepository.findById(id).orElse(null)
                : null;
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