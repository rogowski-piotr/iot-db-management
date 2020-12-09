package pl.piotr.iotdbmanagement.service.sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.sensors.TemperatureOut;
import pl.piotr.iotdbmanagement.repository.sensors.TemperatureOutRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TemperatureOutService {

    private TemperatureOutRepository repository;

    @Autowired
    public TemperatureOutService(TemperatureOutRepository repository) {
        this.repository = repository;
    }

    public List<TemperatureOut> findAll() {
        return repository.findAll();
    }

    public Optional<TemperatureOut> find(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public TemperatureOut create(TemperatureOut temperatureOut) {
        return repository.save(temperatureOut);
    }

}
