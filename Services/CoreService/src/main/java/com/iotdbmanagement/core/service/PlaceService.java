package com.iotdbmanagement.core.service;

import com.iotdbmanagement.core.measurement.MeasurementRepository;
import com.iotdbmanagement.core.place.Place;
import com.iotdbmanagement.core.place.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    private MeasurementRepository measurementRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, MeasurementRepository measurementRepository) {
        this.placeRepository = placeRepository;
        this.measurementRepository = measurementRepository;
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public Optional<Place> find(Long id) {
        return placeRepository.findById(id);
    }

    @Transactional
    public Place create(Place place) {
        return placeRepository.save(place);
    }

    @Transactional
    public Place update(Place place) {
        return placeRepository.save(place);
    }

    @Transactional
    public void delete(Long id) {
        placeRepository.deleteById(id);
        measurementRepository.deleteAllByPlaceIsNullAndSensorIsNull();
    }

    public boolean isUniqueDescription(String newDescription) {
        return placeRepository.findByDescription(newDescription).isEmpty();
    }

    public boolean isUniqueDescription(Place actualPlace, String newDescription) {
        List<Place> allPlacesWithThisDescription = placeRepository.findAllByDescription(newDescription);
        return allPlacesWithThisDescription.stream().noneMatch(place -> !actualPlace.equals(place));
    }

}