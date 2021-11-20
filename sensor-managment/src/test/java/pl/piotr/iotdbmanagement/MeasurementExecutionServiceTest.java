package pl.piotr.iotdbmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;
import pl.piotr.iotdbmanagement.sensorfailure.SensorCurrentFailure;
import pl.piotr.iotdbmanagement.sensorfailure.SensorFailureRepository;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.service.MeasurementExecutionService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SensorManagementApplication.class)
@TestPropertySource(locations="classpath:test.properties")
public class MeasurementExecutionServiceTest {

	@Autowired
	private MeasurementExecutionService service;

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private SensorFailureRepository sensorFailureRepository;

	@Test
	public void verificationToActivateTest() {
		Sensor sensor = sensorRepository.findById(1L).get();
		sensor.setIsActive(false);
		sensorRepository.save(sensor);
		SensorCurrentFailure currentFailure = sensorFailureRepository.save(new SensorCurrentFailure(sensor));

		service.verifyToActivate(sensor);

		assertFalse(sensorFailureRepository.findById(currentFailure.getId()).isPresent());
		assertTrue(sensorRepository.findById(sensor.getId()).get().getIsActive());
	}

}
