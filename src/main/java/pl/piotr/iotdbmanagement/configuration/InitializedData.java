package pl.piotr.iotdbmanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.sensors.TemperatureIn;
import pl.piotr.iotdbmanagement.entity.sensors.TemperatureOut;
import pl.piotr.iotdbmanagement.service.MeasurmentDateService;
import pl.piotr.iotdbmanagement.service.PlaceService;
import pl.piotr.iotdbmanagement.service.sensors.TemperatureInService;
import pl.piotr.iotdbmanagement.service.sensors.TemperatureOutService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class InitializedData {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private MeasurmentDateService measurmentDateService;
    private PlaceService placeService;
    private TemperatureInService temperatureInService;
    private TemperatureOutService temperatureOutService;

    @Autowired
    public InitializedData(MeasurmentDateService measurmentDateService, PlaceService placeService,
                           TemperatureInService temperatureInService, TemperatureOutService temperatureOutService) {
        this.measurmentDateService = measurmentDateService;
        this.placeService = placeService;
        this.temperatureInService = temperatureInService;
        this.temperatureOutService = temperatureOutService;
    }

    @PostConstruct
    private synchronized void init() {
        logger.info("Initializing data");

        MeasurmentDate date = MeasurmentDate.builder()
                .timestamp(LocalDateTime.now())
                .build();

        Place place = Place.builder()
                .description("kitchen")
                .positionX(10)
                .positionY(15)
                .positionZ(20)
                .build();

        TemperatureIn temperatureIn = TemperatureIn.builder()
                .measurmentDate(date)
                .place(place)
                .value((float) 23.26)
                .build();

        TemperatureOut temperatureOut = TemperatureOut.builder()
                .measurmentDate(date)
                .place(place)
                .value((float) -0.96)
                .build();

        measurmentDateService.create(date);
        placeService.create(place);
        temperatureInService.create(temperatureIn);
        temperatureOutService.create(temperatureOut);

    }

}
