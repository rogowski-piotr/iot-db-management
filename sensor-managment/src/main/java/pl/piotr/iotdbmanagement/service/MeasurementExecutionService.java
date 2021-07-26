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

        if (sensor.getConsecutiveFailures() > acceptableFailures) {
            sensor.setIsActive(false);
            isDeactivated = true;
        }
        sensorRepository.save(sensor);
        return isDeactivated;
    }

    public List<Sensor> findSensorsToMeasure(MeasurementsFrequency measurementsFrequency) {
        return sensorRepository.findAllByMeasurementsFrequencyAndIsActive(measurementsFrequency, true);
    }

    public int getDefaultSensorTimeout() {
        if (sensorSettingsRepository.findByName("default").isPresent()) {
            return sensorSettingsRepository.findByName("default").get().getRequestTimeout();
        } else {
            logger.warning("Can not find default settings for sensors, timeout set as 5000ms");
            return 5000;
        }
    }

}
