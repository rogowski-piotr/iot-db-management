package com.iotdbmanagement.core.controller;

import com.iotdbmanagement.core.enums.MeasurementsFrequency;
import com.iotdbmanagement.core.measurementtype.MeasurementType;
import com.iotdbmanagement.core.place.Place;
import com.iotdbmanagement.core.sensor.Sensor;
import com.iotdbmanagement.core.sensorsettings.SensorSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.iotdbmanagement.core.dto.sensor.CreateSensorRequest;
import com.iotdbmanagement.core.dto.sensor.GetSensorResponse;
import com.iotdbmanagement.core.dto.sensor.GetSensorsResponse;
import com.iotdbmanagement.core.dto.sensor.UpdateSensorRequest;
import com.iotdbmanagement.core.service.MeasurementTypeService;
import com.iotdbmanagement.core.service.SensorService;
import com.iotdbmanagement.core.service.SensorSettingsService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api_auth/sensors")
public class SensorController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorService sensorService;
    private SensorSettingsService sensorSettingsService;
    private MeasurementTypeService measurementTypeService;

    @Autowired
    public SensorController(SensorService sensorService, MeasurementTypeService measurementTypeService, SensorSettingsService sensorSettingsService) {
        this.sensorService = sensorService;
        this.sensorSettingsService = sensorSettingsService;
        this.measurementTypeService = measurementTypeService;
    }

    @GetMapping
    public ResponseEntity<Iterable<GetSensorsResponse.Sensor>> getAllSensors(@RequestParam(required = false, name = "item_limit") Integer itemLimit,
                                                                             @RequestParam(required = false, name = "page") Integer page,
                                                                             @RequestParam(required = false, name = "measurement_type") String measurementType,
                                                                             @RequestParam(required = false, name = "measurement_frequency") MeasurementsFrequency measurementsFrequency,
                                                                             @RequestParam(required = false, name = "isActive") Boolean isActive) {
        logger.info(MessageFormat.format("GET all sensors, item_limit: {0}, page: {1}, measurement_type: {2}, measurement_frequency: {3}, isActive {4}", itemLimit, page, measurementType, measurementsFrequency, isActive));
        if ((measurementType != null) && (!measurementTypeService.exist(measurementType))) {
            return ResponseEntity.badRequest().build();
        }
        MeasurementType type = measurementTypeService.getTypeOfString(measurementType);
        List<Sensor> resultList = sensorService.findAndFilterAll(type, measurementsFrequency, isActive, itemLimit, page);
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
        logger.info("CREATE" + request.getMeasurementType());
        Optional<Sensor> newSensor = sensorService.findBySocket(request.getSocket());
        Optional<SensorSettings> sensorSettingsOptional = sensorSettingsService.findByIdOrElseGetDefault(request.getSensorSettingsId());
        Place place = sensorService.findPlace(request.getActualPositionPlaceId());
        MeasurementType type = measurementTypeService.getTypeOfString(request.getMeasurementType());
        if (newSensor.isEmpty() && sensorSettingsOptional.isPresent() && place != null && type != null) {
            Sensor sensor = CreateSensorRequest
                    .dtoToEntityMapper()
                    .apply(request, place, type, sensorSettingsOptional.get());
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
        Optional<SensorSettings> sensorSettingsOptional = sensorSettingsService.findByIdOrElseGetDefault(request.getSensorSettingsId());
        Place newPosition = sensorService.findPlace(request.getActualPosition());
        MeasurementType type = measurementTypeService.getTypeOfString(request.getMeasurementType());
        if (sensor.isPresent() && sensorSettingsOptional.isPresent() && newPosition != null && type != null) {
            UpdateSensorRequest
                    .dtoToEntityUpdater()
                    .apply(sensor.get(), request, newPosition, type, sensorSettingsOptional.get());
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