package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementTypeRepository;

@Service
public class MeasurementTypeService {
    private MeasurementTypeRepository measurementTypeRepository;

    @Autowired
    public MeasurementTypeService(MeasurementTypeRepository measurementTypeRepository) {
        this.measurementTypeRepository = measurementTypeRepository;
    }

    public MeasurementType getTypeOfString(String type) {
        return measurementTypeRepository.findByType(type).orElse(null);
    }

    @Transactional
    public MeasurementType create(String newMeasurementTypeStr) {
        MeasurementType newType = MeasurementType.builder()
                .type(newMeasurementTypeStr)
                .build();
        return measurementTypeRepository.save(newType);
    }

}