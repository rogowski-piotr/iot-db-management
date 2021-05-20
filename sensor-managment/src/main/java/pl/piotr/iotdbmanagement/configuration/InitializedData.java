package pl.piotr.iotdbmanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.service.MeasurementService;
import pl.piotr.iotdbmanagement.service.MeasurementTypeService;
import pl.piotr.iotdbmanagement.service.PlaceService;
import pl.piotr.iotdbmanagement.service.SensorService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class InitializedData {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private PlaceService placeService;
    private SensorService sensorService;
    private MeasurementService measurementService;
    private MeasurementTypeService measurementTypeService;

    @Autowired
    public InitializedData(PlaceService placeService, SensorService sensorService,
                           MeasurementService measurementService, MeasurementTypeService measurementTypeService) {
        this.placeService = placeService;
        this.sensorService = sensorService;
        this.measurementService = measurementService;
        this.measurementTypeService = measurementTypeService;
    }

    @PostConstruct
    private synchronized void init() {
        logger.info("Initializing data");

        measurementTypeService.create("TEMPERATURE");
        measurementTypeService.create("HUMIDITY");
        measurementTypeService.create("TEMPERATURE_AND_HUMIDITY");

        Place placeKitchen = Place.builder()
                .description("kitchen")
                .positionX(10)
                .positionY(15)
                .positionZ(20)
                .build();

        Place placeLobby = Place.builder()
                .description("lobby")
                .build();

        Place placeLivingRoom = Place.builder()
                .description("living room")
                .build();

        placeService.create(placeKitchen);
        placeService.create(placeLivingRoom);
        placeService.create(placeLobby);


        Sensor sensor1 = Sensor.builder()
                .socket("192.168.0.19:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(placeKitchen)
                .measurementType(measurementTypeService.getTypeOfString("TEMPERATURE_AND_HUMIDITY"))
                .isActive(true)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Sensor sensor2 = Sensor.builder()
                .socket("192.168.0.20:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(placeKitchen)
                .measurementType(measurementTypeService.getTypeOfString("TEMPERATURE"))
                .isActive(false)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Sensor sensor3 = Sensor.builder()
                .socket("192.168.0.21:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(placeLobby)
                .measurementType(measurementTypeService.getTypeOfString("HUMIDITY"))
                .isActive(false)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Sensor sensor4 = Sensor.builder()
                .socket("192.168.0.22:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(placeLobby)
                .measurementType(measurementTypeService.getTypeOfString("TEMPERATURE"))
                .isActive(false)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Sensor sensor5 = Sensor.builder()
                .socket("192.168.0.23:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(placeLivingRoom)
                .measurementType(measurementTypeService.getTypeOfString("TEMPERATURE"))
                .isActive(false)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        Sensor sensor6 = Sensor.builder()
                .socket("192.168.0.24:50007")
                .lastMeasurment(LocalDateTime.now())
                .actualPosition(placeLivingRoom)
                .measurementType(measurementTypeService.getTypeOfString("TEMPERATURE_AND_HUMIDITY"))
                .isActive(false)
                .measurementsFrequency(MeasurementsFrequency.ONCE_PER_MINUTE)
                .build();

        sensorService.create(sensor1);
        sensorService.create(sensor2);
        sensorService.create(sensor3);
        sensorService.create(sensor4);
        sensorService.create(sensor5);
        sensorService.create(sensor6);


        Random random = new Random();
        List<Sensor> allSensors = Arrays.asList(sensor1, sensor2, sensor3, sensor3, sensor4, sensor5, sensor6);
        List<Place> allPlace = Arrays.asList(placeKitchen, placeLivingRoom, placeLobby);

        for (int i = 0; i < 7; i++) {
            Sensor randomSensor = allSensors.get(random.nextInt(allSensors.size()));
            Measurement measurementTemp = Measurement.builder()
                    .sensor(randomSensor)
                    .date(LocalDateTime.now().minusDays(random.nextInt(100)))
                    .place(allPlace.get(random.nextInt(allPlace.size())))
                    .value(15 + random.nextFloat() * (30 - 15))
                    .measurementType(randomSensor.getMeasurementType())
                    .build();
            measurementService.create(measurementTemp);
        }

    }

}
