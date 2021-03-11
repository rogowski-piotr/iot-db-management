package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.repository.PlaceRepository;

import java.util.List;
import java.util.Optional;

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

}
