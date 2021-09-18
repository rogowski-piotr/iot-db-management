package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotr.iotdbmanagement.dto.sensor.GetSensorResponse;
import pl.piotr.iotdbmanagement.dto.sensor.GetSensorsResponse;
import pl.piotr.iotdbmanagement.dto.stats.GetAllStatsResponse;
import pl.piotr.iotdbmanagement.service.StatsService;
import java.util.logging.Logger;

@RestController
@RequestMapping("api_auth/stats")
public class StatsController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public ResponseEntity<GetAllStatsResponse> getAllStats() {
        logger.info("GET all stats");
        int successConnections = statsService.getAllSuccessStats();
        int successFailures = statsService.getAllFailuresStats();
        return ResponseEntity.ok(GetAllStatsResponse.entityToDtoMapper().apply(successConnections, successFailures));
    }
}
