package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.piotr.iotdbmanagement.dto.measurment.CreateMeasurementTypeRequest;
import pl.piotr.iotdbmanagement.dto.measurment.GetMeasurementResponse;
import pl.piotr.iotdbmanagement.dto.measurment.GetMeasurementsResponse;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.service.MeasurementService;
import pl.piotr.iotdbmanagement.service.MeasurementTypeService;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/measurements")
public class MeasurementController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private MeasurementService measurementService;
    private MeasurementTypeService measurementTypeService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementTypeService measurementTypeService) {
        this.measurementService = measurementService;
        this.measurementTypeService = measurementTypeService;
    }

    @GetMapping("/available-types")
    public ResponseEntity<List<String>> getAvailableTypes() {
        return ResponseEntity.ok(measurementTypeService.getAllTypes());
    }

    @PostMapping("/new-type")
    public ResponseEntity<Void> createPlace(@RequestBody CreateMeasurementTypeRequest request, UriComponentsBuilder builder) {
        logger.info("CREATE NEW MEASUREMENT TYPE");
        if (!measurementTypeService.exist(request.getType())) {
            measurementTypeService.create(request.getType());
            return ResponseEntity.created(builder.pathSegment("api", "measurements", "available-types").build().toUri()).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<GetMeasurementsResponse.Measurement>> getAllMeasurements(@RequestParam(required = false, name = "item_limit") Integer itemLimit,
                                                                                            @RequestParam(required = false, name = "page") Integer page,
                                                                                            @RequestParam(required = false, name = "measurement_type") String measurementType,
                                                                                            @RequestParam(required = false, name = "sensor_id") Long sensorId,
                                                                                            @RequestParam(required = false, name = "place_id") Long placeId,
                                                                                            @RequestParam(required = false, name = "date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeFrom,
                                                                                            @RequestParam(required = false, name = "date_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeTo) {
        logger.info(MessageFormat.format("GET all measurements, item_limit: {0}, page: {1}, measurement_type: {2}, sensorId {3}, placeId {4}, dateTimeFrom {5}, dateTimeTo {6}",
                    itemLimit, page, measurementType, sensorId, placeId, dateTimeFrom, dateTimeTo));
        if ((measurementType != null) && (!measurementTypeService.exist(measurementType))) {
            return ResponseEntity.badRequest().build();
        }
        MeasurementType type = measurementTypeService.getTypeOfString(measurementType);
        List<Measurement> resultList = measurementService.findAndFilterAll(itemLimit, page, type, sensorId, placeId, dateTimeFrom, dateTimeTo);
        return resultList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetMeasurementsResponse.entityToDtoMapper().apply(resultList));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetMeasurementResponse> getSingleSensor(@PathVariable(name = "id") UUID id) {
        logger.info("GET single , id: " + id);
        Optional<Measurement> measurementOptional = measurementService.findOne(id);
        return measurementOptional
                .map(measurement -> ResponseEntity.ok(GetMeasurementResponse.entityToDtoMapper().apply(measurement)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMeasurements(@RequestParam(required = false, name = "date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeFrom,
                                                   @RequestParam(required = false, name = "date_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeTo) {
        logger.info(MessageFormat.format("DELETE measurements in range, date_from: {0}, date_to: {1}", dateTimeFrom, dateTimeTo));
        return measurementService.deleteInRange(dateTimeFrom, dateTimeTo)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable("id") UUID id) {
        logger.info(MessageFormat.format("DELETE measurement, id: {0}", id));
        Optional<Measurement> measurementOptional = measurementService.findOne(id);
        if (measurementOptional.isPresent()) {
            measurementService.deleteOne(measurementOptional.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
