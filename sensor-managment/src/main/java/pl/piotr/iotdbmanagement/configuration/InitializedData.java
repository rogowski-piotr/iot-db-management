package pl.piotr.iotdbmanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.measurement.MeasurementRepository;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementTypeRepository;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.place.PlaceRepository;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class InitializedData {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private PlaceRepository placeRepository;
    private SensorRepository sensorRepository;
    private MeasurementRepository measurementRepository;
    private MeasurementTypeRepository measurementTypeRepository;

    private static final int MEASUREMENT_AMOUNT = 15;
    List<String> types = List.of("TEMPERATURE", "HUMIDITY", "TEMPERATURE_AND_HUMIDITY");
    List<String> descriptions = List.of("room1", "room2", "room3", "room4");
    List<String> sockets = List.of("192.168.0.10:50007", "192.168.0.11:50007", "192.168.0.12:50007", "192.168.0.13:50007",
                                    "192.168.0.14:50007", "192.168.0.15:50007", "192.168.0.16:50007", "192.168.0.17:50007");

    @Autowired
    public InitializedData(PlaceRepository placeRepository,
                           SensorRepository sensorRepository,
                           MeasurementRepository measurementRepository,
                           MeasurementTypeRepository measurementTypeRepository) {
        this.placeRepository = placeRepository;
        this.sensorRepository = sensorRepository;
        this.measurementRepository = measurementRepository;
        this.measurementTypeRepository = measurementTypeRepository;
    }

    @PostConstruct
    private synchronized void init() {
        logger.info("Initializing data");

        List<MeasurementType> allMeasurementTypes = new ArrayList<>();
        List<Place> allPlaces = new ArrayList<>();
        List<Sensor> allSensors = new ArrayList<>();
        Random random = new Random();

        for (String typeString : types) {
            MeasurementType type = MeasurementType.builder()
                    .type(typeString)
                    .build();
            measurementTypeRepository.save(type);
            allMeasurementTypes.add(type);
        }

        for (String placeString : descriptions) {
            Place place = Place.builder()
                    .description(placeString)
                    .positionX(random.nextInt(1000))
                    .positionY(random.nextInt(1000))
                    .positionZ(random.nextInt(1000))
                    .build();
            placeRepository.save(place);
            allPlaces.add(place);
        }

        for (String socketString : sockets) {
            Place place = allPlaces.get(random.nextInt(allPlaces.size()));
            Sensor sensor = Sensor.builder()
                    .socket(socketString)
                    .isActive(random.nextBoolean())
                    .measurementsFrequency(MeasurementsFrequency.values()[random.nextInt(MeasurementsFrequency.values().length)])
                    .measurementType(allMeasurementTypes.get(random.nextInt(allMeasurementTypes.size())).getType())
                    .lastMeasurment(LocalDateTime.now().minusDays(random.nextInt(100)))
                    .actualPosition(place)
                    .build();
            sensorRepository.save(sensor);
            allSensors.add(sensor);

            List<Sensor> sensors = place.getSensors() != null ? place.getSensors() : new ArrayList<>();
            sensors.add(sensor);
            place.setSensors(sensors);
            placeRepository.save(place);
        }

        for (int i = 0; i < MEASUREMENT_AMOUNT; i++) {
            Sensor sensor = allSensors.get(random.nextInt(allSensors.size()));
            Measurement measurement = Measurement.builder()
                    .value(15 + random.nextFloat() * (30 - 15))
                    .date(sensor.getLastMeasurment().minusDays(random.nextInt(10)))
                    .measurementType(sensor.getMeasurementType())
                    .sensor(sensor)
                    .place(sensor.getActualPosition())
                    .build();
            measurementRepository.save(measurement);

            List<Measurement> measurementsForPlace = sensor.getActualPosition().getMeasurements() != null ? sensor.getActualPosition().getMeasurements() : new ArrayList<>();
            measurementsForPlace.add(measurement);
            sensor.getActualPosition().setMeasurements(measurementsForPlace);
            placeRepository.save(sensor.getActualPosition());

            List<Measurement> measurementsForSensor = sensor.getMeasurements() != null ? sensor.getMeasurements() : new ArrayList<>();
            measurementsForSensor.add(measurement);
            sensor.setMeasurements(measurementsForSensor);
            sensorRepository.save(sensor);
        }

    }

}
