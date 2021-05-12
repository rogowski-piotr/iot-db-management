package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.repository.PlaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private PlaceRepository repository;

    @Autowired
    public PlaceService(PlaceRepository repository) {
        this.repository = repository;
    }

    public List<Place> findAll() {
        return repository.findAll();
    }

    public Optional<Place> find(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Place create(Place place) {
        return repository.save(place);
    }

    @Transactional
    public Place update(Place place) {
        return repository.save(place);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean isUniqueDescription(String newDescription) {
        return repository.findByDescription(newDescription).isEmpty();
    }

    public boolean isUniqueDescription(Place actualPlace, String newDescription) {
        List<Place> allPlacesWithThisDescription = repository.findAllByDescription(newDescription);
        return allPlacesWithThisDescription.stream().noneMatch(place -> !actualPlace.equals(place));
    }

}
