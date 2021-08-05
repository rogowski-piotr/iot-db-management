package pl.piotr.iotdbmanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public MeasurementExecutionService(MeasurementRepository measurementRepository, MeasurementTypeRepository measurementTypeRepository,
                                       SensorRepository sensorRepository, SensorSettingsRepository sensorSettingsRepository,
                                       SensorFailureRepository sensorFailureRepository) {
        this.measurementRepository = measurementRepository;
        this.measurementTypeRepository = measurementTypeRepository;
        this.sensorRepository = sensorRepository;
        this.sensorSettingsRepository = sensorSettingsRepository;
        this.sensorFailureRepository = sensorFailureRepository;
    }

    public MeasurementType getMeasurementTypeByString(String type) {
        return measurementTypeRepository.findByType(type).orElse(null);
    }

    @Transactional
    public Measurement addMeasurement(Measurement measurement) {
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
        List<Sensor> notActiveSensors = sensorRepository.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, false);

        notActiveSensors.forEach(sensor -> {
            Optional<SensorCurrentFailure> sensorFailureOptional = sensorFailureRepository.findFirstBySensor(sensor);
            SensorCurrentFailure sensorCurrentFailure = sensorFailureOptional.orElseGet(() -> new SensorCurrentFailure(sensor));
            if (sensorCurrentFailure.getLeftCyclesToRefresh() <= 0) {
                sensorCurrentFailure.setActivityVerification(true);
            }
            sensorCurrentFailure.decrementLeftCycleToRefresh();
            sensorFailureRepository.save(sensorCurrentFailure);
        });

        List<Sensor> sensorsToBeVerified = sensorFailureRepository.findAllByActivityVerificationTrue()
                .stream().map(SensorCurrentFailure::getSensor).collect(Collectors.toList());
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
            Optional<SensorCurrentFailure> sensorFailureOptional = sensorFailureRepository.findFirstBySensor(sensor);
            SensorCurrentFailure sensorCurrentFailure = sensorFailureOptional.orElseGet(() -> new SensorCurrentFailure(sensor));
            sensorFailureRepository.delete(sensorCurrentFailure);
        }
    }

}
