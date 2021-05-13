package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.measurement.MeasurementRepository;
import pl.piotr.iotdbmanagement.place.PlaceRepository;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private MeasurementRepository measurementRepository;
    private SensorRepository sensorRepository;
    private PlaceRepository placeRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, PlaceRepository placeRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.placeRepository = placeRepository;
    }

    public Optional<Measurement> findOne(UUID id) {
        return measurementRepository.findById(id);
    }

    public List<Measurement> findAndFilterAll(Integer itemLimit, Integer page, MeasurementType measurementType,
                                              Long sensorId, Long placeId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Measurement> resultList;
        if (measurementType != null) {
            resultList = measurementRepository.findAllByMeasurementType(measurementType);

        } else if (sensorId != null && placeId != null) {
            Optional<Sensor> sensorOptional = sensorRepository.findById(sensorId);
            Optional<Place> placeOptional = placeRepository.findById(placeId);
            if (sensorOptional.isPresent() && placeOptional.isPresent()) {
                resultList = measurementRepository.findAllBySensorAndPlace(sensorOptional.get(), placeOptional.get());
            } else resultList = new ArrayList<>();

        } else if (sensorId != null) {
            Optional<Sensor> sensorOptional = sensorRepository.findById(sensorId);
            if (sensorOptional.isPresent()) {
                resultList = measurementRepository.findAllBySensor(sensorOptional.get());
            } else resultList = new ArrayList<>();

        } else if (placeId != null) {
            Optional<Place> placeOptional = placeRepository.findById(placeId);
            if (placeOptional.isPresent()) {
                resultList = measurementRepository.findAllByPlace(placeOptional.get());
            } else resultList = new ArrayList<>();

        } else {
            resultList = measurementRepository.findAll();
        }

        if (dateFrom != null) {
            resultList = resultList.stream()
                    .filter(measurement ->
                        measurement.getDate() != null && measurement.getDate().compareTo(dateFrom) >= 0)
                    .collect(Collectors.toList());
        }

        if (dateTo != null) {
            resultList = resultList.stream()
                    .filter(measurement ->
                            measurement.getDate() != null && measurement.getDate().compareTo(dateTo) <= 0)
                    .collect(Collectors.toList());
        }

        resultList.sort((o1, o2) -> {
            if (o1.getDate() != null && o2.getDate() != null) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return 1;
            }
        });

        return getPageInLimit(resultList, itemLimit, page);
    }

    private List<Measurement> getPageInLimit(List<Measurement> list, Integer limit, Integer page) {
        if (limit == null) limit = 10;
        if (page == null) page = 0;
        int indexFrom = page * limit;
        int indexTo = indexFrom + limit;
        try {
            list = list.subList(indexFrom, indexTo > list.size() ? list.size() : indexTo);
        } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
            list = List.of();
        }
        return list;
    }

    @Transactional
    public Measurement create(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    @Transactional
    public void deleteOne(UUID id) {
        measurementRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteInRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        List<Measurement> allMeasurements = measurementRepository.findAll();

        if (dateTimeFrom != null) {
            allMeasurements = allMeasurements.stream()
                    .filter(measurement ->
                            measurement.getDate() != null && measurement.getDate().compareTo(dateTimeFrom) >= 0)
                    .collect(Collectors.toList());
        }

        if (dateTimeTo != null) {
            allMeasurements = allMeasurements.stream()
                    .filter(measurement ->
                            measurement.getDate() != null && measurement.getDate().compareTo(dateTimeTo) <= 0)
                    .collect(Collectors.toList());
        }

        measurementRepository.deleteAll(allMeasurements);
        return allMeasurements.isEmpty();
    }

}
