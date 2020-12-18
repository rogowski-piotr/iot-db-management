package pl.piotr.iotdbmanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.service.MeasurmentDateService;
import pl.piotr.iotdbmanagement.service.MeasurmentService;
import pl.piotr.iotdbmanagement.service.PlaceService;
import pl.piotr.iotdbmanagement.service.SensorService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class InitializedData {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private MeasurmentDateService measurmentDateService;
    private PlaceService placeService;
    private SensorService sensorService;
    private MeasurmentService measurmentService;

    @Autowired
    public InitializedData(MeasurmentDateService measurmentDateService, PlaceService placeService,
                           SensorService sensorService, MeasurmentService measurmentService) {
        this.measurmentDateService = measurmentDateService;
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

        MeasurmentDate date = new MeasurmentDate(LocalDateTime.now());

        Sensor sensor = Sensor.builder()
                .socket("192.168.0.19:50007")
                .lastMeasurment(date)
                .actualPosition(place)
                .measurementType(MeasurementType.TEMPERATURE_AND_HUMIDITY)
                .state(true)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Measurment measurment = Measurment.builder()
                .sensor(sensor)
                .measurmentDate(date)
                .place(place)
                .value((float) 21.5)
                .build();

        measurmentDateService.create(date);
        placeService.create(place);
        sensorService.create(sensor);
        measurmentService.create(measurment);
    }

}
