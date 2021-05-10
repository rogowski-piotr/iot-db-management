package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.piotr.iotdbmanagement.dto.sensor.CreateSensorRequest;
import pl.piotr.iotdbmanagement.dto.sensor.GetSensorResponse;
import pl.piotr.iotdbmanagement.dto.sensor.GetSensorsResponse;
import pl.piotr.iotdbmanagement.dto.sensor.UpdateSensorRequest;
import pl.piotr.iotdbmanagement.entity.Measurement;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.service.SensorService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/sensors")
public class SensorController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public ResponseEntity<Iterable<GetSensorsResponse.Sensor>> getAllSensors(@RequestParam(required = false, name = "item_limit") Integer itemLimit,
                                                                             @RequestParam(required = false, name = "page") Integer page,
                                                                             @RequestParam(required = false, name = "measurement_type") MeasurementType measurementType,
                                                                             @RequestParam(required = false, name = "measurement_frequency") MeasurementsFrequency measurementsFrequency,
                                                                             @RequestParam(required = false, name = "isActive") Boolean isActive) {
        logger.info(MessageFormat.format("GET all sensors, item_limit: {0}, page: {1}, measurement_type: {2}, measurement_frequency: {3}, isActive {4}", itemLimit, page, measurementType, measurementsFrequency, isActive));
        List<Sensor> resultList = sensorService.findAndFilterAll(measurementType, measurementsFrequency, isActive, itemLimit, page);
        return resultList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetSensorsResponse.entityToDtoMapper().apply(resultList));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetSensorResponse> getSingleSensor(@PathVariable(name = "id") Long id) {
        logger.info("GET single sensor, id: " + id);
        Optional<Sensor> sensorOptional = sensorService.find(id);
        return sensorOptional
                .map(sensor -> ResponseEntity.ok(GetSensorResponse.entityToDtoMapper().apply(sensor)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Void> createSensor(@RequestBody CreateSensorRequest request, UriComponentsBuilder builder) {
        logger.info("CREATE");
        Optional<Sensor> newSensor = sensorService.findBySocket(request.getSocket());
        Place newPosition = sensorService.findPlace(request.getActualPositionPlaceId());
        if (newSensor.isEmpty() && newPosition != null) {
            Sensor sensor = CreateSensorRequest
                    .dtoToEntityMapper()
                    .apply(request, newPosition);
            sensorService.create(sensor);
            return ResponseEntity.created(builder.pathSegment("api", "sensors")
                    .buildAndExpand(sensor.getId()).toUri()).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateSensor(@RequestBody UpdateSensorRequest request, @PathVariable("id") Long id) {
        logger.info("UPDATE");
        Optional<Sensor> sensor = sensorService.find(id);
        Place newPosition = sensorService.findPlace(request.getActualPosition());
        if (sensor.isPresent() && newPosition != null) {
            UpdateSensorRequest
                    .dtoToEntityUpdater()
                    .apply(sensor.get(), request, newPosition);
            sensorService.update(sensor.get());
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable("id") Long id) {
        logger.info(MessageFormat.format("DELETE sensor, id: {0}", id));
        Optional<Sensor> sensorOptional = sensorService.find(id);
        if (sensorOptional.isPresent()) {
            sensorService.delete(sensorOptional.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}