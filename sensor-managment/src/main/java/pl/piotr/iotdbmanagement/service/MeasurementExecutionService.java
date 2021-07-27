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
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettingsRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class MeasurementExecutionService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SensorSettingsRepository sensorSettingsRepository;
    private MeasurementRepository measurementRepository;
    private MeasurementTypeRepository measurementTypeRepository;
    private SensorRepository sensorRepository;

    public MeasurementExecutionService(MeasurementRepository measurementRepository, MeasurementTypeRepository measurementTypeRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.measurementTypeRepository = measurementTypeRepository;
        this.sensorRepository = sensorRepository;
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
        sensor.setConsecutiveFailures(sensor.getConsecutiveFailures() + 1);
        int acceptableFailures = sensor.getSensorSettings().getAcceptableConsecutiveFailures();

        if (sensor.getConsecutiveFailures() > acceptableFailures || sensor.getActivityVerification()) {
            sensor.setIsActive(false);
            sensor.setLeftCyclesToRefresh(sensor.getSensorSettings().getCyclesToRefresh());
            sensor.setActivityVerification(false);
            isDeactivated = true;
        }
        sensorRepository.save(sensor);
        return isDeactivated;
    }

    @Transactional
    public List<Sensor> findSensorsToMeasure(MeasurementsFrequency measurementsFrequency) {
        List<Sensor> notActive = sensorRepository.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, false);

        notActive.forEach(sensor -> {
            if (sensor.getLeftCyclesToRefresh() <= 0) {
                sensor.setActivityVerification(true);
            }
            int leftCycles = sensor.getLeftCyclesToRefresh() == 0 ? 0 : sensor.getLeftCyclesToRefresh() - 1;
            sensor.setLeftCyclesToRefresh(leftCycles);
            sensorRepository.save(sensor);
        });

        return sensorRepository.findAllByMeasurementsFrequencyAndIsActiveOrActivityVerification(measurementsFrequency.toString());
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
            sensor.setActivityVerification(false);
            sensor.setConsecutiveFailures(0);
            sensor.setLeftCyclesToRefresh(0);
            sensorRepository.save(sensor);
        }
    }

}
