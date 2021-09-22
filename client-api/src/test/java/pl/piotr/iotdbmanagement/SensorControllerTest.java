package pl.piotr.iotdbmanagement;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.test.context.TestPropertySource;
import pl.piotr.iotdbmanagement.dto.sensor.CreateSensorRequest;
import pl.piotr.iotdbmanagement.dto.sensor.UpdateSensorRequest;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.sensor.SensorRepository;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
public class SensorControllerTest {

    @Autowired
    private SensorRepository repository;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void GetSensors_thenStatus200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("admin", "admin")
                .getForEntity("/api_auth/sensors", String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void given_whenGetSensors_thenReturnJsonArray() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("admin", "admin")
                .getForEntity("/api_auth/sensors", String.class);

        DocumentContext context = JsonPath.parse(result.getBody());
        int responseLength = context.read("$.length()");

        List<Sensor> allSensors = repository.findAll();
        int repositoryLength = allSensors.size();

        assertEquals(responseLength, repositoryLength);
    }

    @Test
    public void givenSensorId_whenGetSensor_thenReturnStatus200() throws Exception {
        Sensor sensor = repository.findAll().get(0);

        ResponseEntity<String> result = template.withBasicAuth("admin", "admin")
                .getForEntity("/api_auth/sensors/" + sensor.getId(), String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenSensorCreateDtoObject_whenPostSensor_thenReturnStatus201AndSaveSensor() throws Exception {
        CreateSensorRequest requestObject = new CreateSensorRequest();
        requestObject.setName("test name");
        requestObject.setSocket("192.168.1.1:50000");
        requestObject.setMeasurementType("TEMPERATURE_AND_HUMIDITY");
        requestObject.setMeasurementsFrequency(MeasurementsFrequency.ONCE_PER_DAY);
        requestObject.setActualPositionPlaceId(1L);

        ResponseEntity<String> result = template.withBasicAuth("admin", "admin")
                .postForEntity("/api_auth/sensors", requestObject, String.class);

        // assert status
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // checking if sensor is save
        Sensor newSensor = repository.findBySocket(requestObject.getSocket()).orElseThrow();
        assertEquals(newSensor.getSocket(), requestObject.getSocket());
    }

    @Test
    public void givenSensor_whenPutSensor_thenReturnStatus202AndUpdate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Sensor sensor = repository.findAll().get(0);

        UpdateSensorRequest requestObject = new UpdateSensorRequest();
        requestObject.setName("updated name");
        requestObject.setSocket(sensor.getSocket());
        requestObject.setMeasurementType(sensor.getMeasurementType().getType());
        requestObject.setMeasurementsFrequency(sensor.getMeasurementsFrequency());
        requestObject.setIsActive(sensor.getIsActive());
        requestObject.setActualPosition(1L);
        HttpEntity<UpdateSensorRequest> requestUpdate = new HttpEntity<>(requestObject, headers);

        ResponseEntity<String> result = template.withBasicAuth("admin", "admin")
                .exchange("/api_auth/sensors/" + sensor.getId(), HttpMethod.PUT, requestUpdate, String.class);

        // assert status
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());

        // checking if sensor is updated
        Sensor updatedSensor = repository.findById(sensor.getId()).orElseThrow();
        assertEquals(updatedSensor.getName(), "updated name");
    }

    @Test
    public void givenSensorId_whenDeleteSensor_thenDelete() throws Exception {
        Sensor sensor = repository.findAll().get(0);

        template.withBasicAuth("admin", "admin")
                .delete("/api_auth/sensors/" + sensor.getId());

        // checking if sensor is deleted
        if (repository.findById(sensor.getId()).isEmpty()) {
            assert true;
        } else {
            assert false;
        }
    }

}
