package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;
import pl.piotr.iotdbmanagement.sensorfailure.SensorCurrentFailure;
import pl.piotr.iotdbmanagement.sensorfailure.SensorFailureRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeasurementExecutionService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorRepository sensorRepository;
    private SensorFailureRepository sensorFailureRepository;

    @Autowired
    public MeasurementExecutionService(SensorRepository sensorRepository, SensorFailureRepository sensorFailureRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorFailureRepository = sensorFailureRepository;
    }

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
}