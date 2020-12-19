package pl.piotr.iotdbmanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.service.MeasurmentService;
import pl.piotr.iotdbmanagement.service.PlaceService;
import pl.piotr.iotdbmanagement.service.SensorService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class InitializedData {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private PlaceService placeService;
    private SensorService sensorService;
    private MeasurmentService measurmentService;

    @Autowired
    public InitializedData(PlaceService placeService, SensorService sensorService,
                           MeasurmentService measurmentService) {
        this.placeService = placeService;
        this.sensorService = sensorService;
        this.measurmentService = measurmentService;
    }

    @PostConstruct
    private synchronized void init() {
        logger.info("Initializing data");

        Place place = Place.builder()
                .description("kitchen")
                .positionX(10)
                .positionY(15)
                .positionZ(20)
                .build();

        Sensor sensor = Sensor.builder()
                .socket("192.168.0.19:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(place)
                .measurementType(MeasurementType.TEMPERATURE_AND_HUMIDITY)
                .state(true)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Measurment measurmentTemp = Measurment.builder()
                .sensor(sensor)
                .date(LocalDateTime.now())
                .place(place)
                .value((float) 21.5)
                .measurementType(MeasurementType.TEMPERATURE)
                .build();

        Measurment measurmentHumi = Measurment.builder()
                .sensor(sensor)
                .date(LocalDateTime.now())
                .place(place)
                .value((float) 50)
                .measurementType(MeasurementType.HUMIDITY)
                .build();

        placeService.create(place);
        sensorService.create(sensor);
        measurmentService.create(measurmentTemp);
        measurmentService.create(measurmentHumi);
    }

}
