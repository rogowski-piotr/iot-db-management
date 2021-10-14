package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.piotr.iotdbmanagement.dto.sensorsettings.GetSensorSettingsResponse;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.service.SensorSettingsService;

import java.text.MessageFormat;
import java.util.List;
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

}
