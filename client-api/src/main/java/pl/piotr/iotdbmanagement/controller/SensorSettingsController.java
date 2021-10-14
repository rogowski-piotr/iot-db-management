package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotr.iotdbmanagement.dto.sensorsettings.GetSensorSettingResponse;
import pl.piotr.iotdbmanagement.dto.sensorsettings.GetSensorSettingsResponse;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.service.SensorSettingsService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api_auth/sensors/settings")
public class SensorSettingsController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorSettingsService sensorSettingsService;

    @Autowired
    public SensorSettingsController(SensorSettingsService sensorSettingsService) {
        this.sensorSettingsService = sensorSettingsService;
    }

    @GetMapping
    public ResponseEntity<Iterable<GetSensorSettingsResponse.SensorSettings>> getAllSensorsSettings(
            @RequestParam(required = false, name = "item_limit") Integer itemLimit,
            @RequestParam(required = false, name = "page") Integer page) {
        logger.info(MessageFormat.format("GET all SensorsSettings, item_limit: {0}, page: {1}", itemLimit, page));
        List<SensorSettings> resultList = sensorSettingsService.findAll(itemLimit, page);
        return resultList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetSensorSettingsResponse.entityToDtoMapper().apply(resultList));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetSensorSettingResponse> getSingleSensorsSettings(@PathVariable(name = "id") Long id) {
        logger.info("GET single SensorsSettings, id: " + id);
        Optional<SensorSettings> sensorSettingsOptional = sensorSettingsService.findOne(id);
        return sensorSettingsOptional
                .map(sensorSettings -> ResponseEntity.ok(GetSensorSettingResponse.entityToDtoMapper().apply(sensorSettings)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}