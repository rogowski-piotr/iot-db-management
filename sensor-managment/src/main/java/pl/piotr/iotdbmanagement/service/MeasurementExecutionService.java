package pl.piotr.iotdbmanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.connectionstats.ConnectionStats;
import pl.piotr.iotdbmanagement.connectionstats.ConnectionStatsRepository;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.measurement.MeasurementRepository;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementTypeRepository;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;
import pl.piotr.iotdbmanagement.sensorfailure.SensorCurrentFailure;
import pl.piotr.iotdbmanagement.sensorfailure.SensorFailureRepository;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettingsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeasurementExecutionService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorSettingsRepository sensorSettingsRepository;
    private MeasurementRepository measurementRepository;
    private MeasurementTypeRepository measurementTypeRepository;
    private SensorRepository sensorRepository;
    private SensorFailureRepository sensorFailureRepository;
    private ConnectionStatsRepository connectionStatsRepository;

    public MeasurementExecutionService(MeasurementRepository measurementRepository, MeasurementTypeRepository measurementTypeRepository,
                                       SensorRepository sensorRepository, SensorSettingsRepository sensorSettingsRepository,
                                       SensorFailureRepository sensorFailureRepository, ConnectionStatsRepository connectionStatsRepository) {
        this.measurementRepository = measurementRepository;
        this.measurementTypeRepository = measurementTypeRepository;
        this.sensorRepository = sensorRepository;
        this.sensorSettingsRepository = sensorSettingsRepository;
        this.sensorFailureRepository = sensorFailureRepository;
        this.connectionStatsRepository = connectionStatsRepository;
    }

    public MeasurementType getMeasurementTypeByString(String type) {
        return measurementTypeRepository.findByType(type).orElse(null);
    }

    @Transactional
    public Measurement addMeasurement(Measurement measurement) {
        Sensor sensor = measurement.getSensor();
        sensor.setLastMeasurment(LocalDateTime.now().plusHours(1));
        sensorRepository.save(sensor);
        return measurementRepository.save(measurement);
    }

    @Transactional
    public boolean verifyToDeactivate(Sensor sensor) {
        boolean isDeactivated = false;
        Optional<SensorCurrentFailure> sensorFailureOptional = sensorFailureRepository.findFirstBySensor(sensor);
        SensorCurrentFailure sensorCurrentFailure = sensorFailureOptional.orElseGet(() -> new SensorCurrentFailure(sensor));
        sensorCurrentFailure.incrementConsecutiveFailures();

        int acceptableFailures = sensor.getSensorSettings().getAcceptableConsecutiveFailures();
        if (sensorCurrentFailure.getConsecutiveFailures() >= acceptableFailures || sensorCurrentFailure.getActivityVerification()) {
            sensor.setIsActive(false);
            sensorCurrentFailure.setLeftCyclesToRefresh(sensor.getSensorSettings().getCyclesToRefresh());
            sensorCurrentFailure.setActivityVerification(false);
            isDeactivated = true;
            sensorRepository.save(sensor);
        }

        sensorFailureRepository.save(sensorCurrentFailure);
        return isDeactivated;
    }

    @Transactional
    public List<Sensor> findSensorsToMeasure(MeasurementsFrequency measurementsFrequency) {
        sensorRepository.findAllByMeasurementsFrequencyAndIsActiveIsFalse(measurementsFrequency)
            .forEach(sensor -> {
                Optional<SensorCurrentFailure> sensorFailureOptional = sensorFailureRepository.findFirstBySensor(sensor);
                SensorCurrentFailure sensorCurrentFailure = sensorFailureOptional.orElseGet(() -> new SensorCurrentFailure(sensor));
                if (sensorCurrentFailure.getLeftCyclesToRefresh() <= 0) {
                    sensorCurrentFailure.setActivityVerification(true);
                }
                sensorCurrentFailure.decrementLeftCycleToRefresh();
                sensorFailureRepository.save(sensorCurrentFailure);
            });

        List<Sensor> sensorsToBeVerified = sensorFailureRepository.findAllByActivityVerificationTrue()
                .stream()
                .map(SensorCurrentFailure::getSensor)
                .filter(sensor -> sensor.getMeasurementsFrequency().equals(measurementsFrequency))
                .collect(Collectors.toList());

        List<Sensor> sensorsActive = sensorRepository.findAllByMeasurementsFrequencyAndIsActiveIsTrue(measurementsFrequency);

        logger.info("Found sensors: " + sensorsActive.size() + " active, " + sensorsToBeVerified.size() + " to verify");
        return Stream.concat(sensorsToBeVerified.stream(), sensorsActive.stream()).collect(Collectors.toList());
    }

    public int getDefaultSensorTimeout() {
        if (sensorSettingsRepository.findByName("default").isPresent()) {
            return sensorSettingsRepository.findByName("default").get().getRequestTimeout();
        } else {
            logger.warning("Can not find default settings for sensors, timeout set as 5000ms");
            return 5000;
        }
    }

    @Transactional
    public void verifyToActivate(Sensor sensor) {
        if (! sensor.getIsActive()) {
            sensor.setIsActive(true);
            sensorRepository.save(sensor);
            sensorFailureRepository.findFirstBySensor(sensor).ifPresent(sensorCurrentFailure -> sensorFailureRepository.delete(sensorCurrentFailure));
        }
    }

    @Transactional
    public void addSuccessfulStats(Sensor sensor) {
        Optional<ConnectionStats> connectionStatsOptional = connectionStatsRepository.findBySensorAndDate(sensor, LocalDate.now());
        connectionStatsOptional.ifPresentOrElse(
                connectionStats ->
                        connectionStats.setSuccessfulConnections(connectionStats.getSuccessfulConnections() + 1),
                () -> {
                    ConnectionStats connectionStats = new ConnectionStats();
                    connectionStats.setSensor(sensor);
                    connectionStats.setDate(LocalDate.now());
                    connectionStats.setFailureConnections(0);
                    connectionStats.setSuccessfulConnections(1);
                    connectionStatsRepository.save(connectionStats);
                }
        );
    }

    @Transactional
    public void addFailureStats(Sensor sensor) {
        Optional<ConnectionStats> connectionStatsOptional = connectionStatsRepository.findBySensorAndDate(sensor, LocalDate.now());
        connectionStatsOptional.ifPresentOrElse(
                connectionStats ->
                        connectionStats.setFailureConnections(connectionStats.getFailureConnections() + 1),
                () -> {
                    ConnectionStats connectionStats = new ConnectionStats();
                    connectionStats.setSensor(sensor);
                    connectionStats.setDate(LocalDate.now());
                    connectionStats.setFailureConnections(1);
                    connectionStats.setSuccessfulConnections(0);
                    connectionStatsRepository.save(connectionStats);
                }
        );
    }

}
