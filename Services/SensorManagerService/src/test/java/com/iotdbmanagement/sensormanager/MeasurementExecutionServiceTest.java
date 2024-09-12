package com.iotdbmanagement.sensormanager;

import com.iotdbmanagement.sensormanager.sensor.Sensor;
import com.iotdbmanagement.sensormanager.sensor.SensorRepository;
import com.iotdbmanagement.sensormanager.sensorfailure.SensorCurrentFailure;
import com.iotdbmanagement.sensormanager.sensorfailure.SensorFailureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.iotdbmanagement.sensormanager.service.MeasurementExecutionService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SensorManagerServiceApplication.class)
@ActiveProfiles("test")
public class MeasurementExecutionServiceTest {

	@Autowired
	private MeasurementExecutionService service;

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private SensorFailureRepository sensorFailureRepository;

	@Test
	public void verificationToActivateTest() {
//		Sensor sensor = sensorRepository.findById(1L).get();
//		sensor.setIsActive(false);
//		sensorRepository.save(sensor);
//		SensorCurrentFailure currentFailure = sensorFailureRepository.save(new SensorCurrentFailure(sensor));
//
//		service.verifyToActivate(sensor);
//
//		assertFalse(sensorFailureRepository.findById(currentFailure.getId()).isPresent());
//		assertTrue(sensorRepository.findById(sensor.getId()).get().getIsActive());
	}

}
