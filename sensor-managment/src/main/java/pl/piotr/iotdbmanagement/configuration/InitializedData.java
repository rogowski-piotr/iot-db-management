package pl.piotr.iotdbmanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.service.MeasurementService;
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
    private MeasurementService measurementService;

    @Autowired
    public InitializedData(PlaceService placeService, SensorService sensorService,
                           MeasurementService measurementService) {
        this.placeService = placeService;
        this.sensorService = sensorService;
        this.measurementService = measurementService;
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
                .isActive(true)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Measurement measurementTemp = Measurement.builder()
                .sensor(sensor)
                .date(LocalDateTime.now())
                .place(place)
                .value((float) 21.5)
                .measurementType(MeasurementType.TEMPERATURE)
                .build();

        Measurement measurementHumi = Measurement.builder()
                .sensor(sensor)
                .date(LocalDateTime.now())
                .place(place)
                .value((float) 50)
                .measurementType(MeasurementType.HUMIDITY)
                .build();

        placeService.create(place);
        sensorService.create(sensor);
        measurementService.create(measurementTemp);
        measurementService.create(measurementHumi);
    }

}
