package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.piotr.iotdbmanagement.dto.place.CreatePlaceRequest;
import pl.piotr.iotdbmanagement.dto.place.GetPlaceResponse;
import pl.piotr.iotdbmanagement.dto.place.GetPlacesResponse;
import pl.piotr.iotdbmanagement.dto.place.UpdatePlaceRequest;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.service.PlaceService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/places")
public class PlaceController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<Iterable<GetPlacesResponse.Place>> getAllPlaces() {
        logger.info("GET all");
        List<Place> resultList = placeService.findAll();
        return resultList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetPlacesResponse.entityToDtoMapper().apply(resultList));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetPlaceResponse> getSinglePlace(@PathVariable(name = "id") String id) {
        logger.info("GET single , id: " + id);
        Optional<Place> placeOptional = placeService.find(id);
        return placeOptional
                .map(place -> ResponseEntity.ok(GetPlaceResponse.entityToDtoMapper().apply(place)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Void> createPlace(@RequestBody CreatePlaceRequest request, UriComponentsBuilder builder) {
        logger.info("CREATE ");
        if (placeService.isUniqueDescription(request.getDescription())) {
            Place place = CreatePlaceRequest.dtoToEntityMapper().apply(request);
            placeService.create(place);
            return ResponseEntity.created(builder.pathSegment("api", "places")
                    .buildAndExpand(place.getId()).toUri()).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePlace(@RequestBody UpdatePlaceRequest request, @PathVariable("id") String id) {
        logger.info("UPDATE");
        Optional<Place> placeOptional = placeService.find(id);
        if (placeOptional.isPresent() && placeService.isUniqueDescription(placeOptional.get(), request.getDescription())) {
            UpdatePlaceRequest.dtoToEntityUpdater().apply(placeOptional.get(), request);
            placeService.update(placeOptional.get());
            return ResponseEntity.accepted().build();
        } else if (placeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") String id) {
        logger.info(MessageFormat.format("DELETE place, id: {0}", id));
        Optional<Place> placeOptional = placeService.find(id);
        if (placeOptional.isPresent()) {
            placeService.delete(placeOptional.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}