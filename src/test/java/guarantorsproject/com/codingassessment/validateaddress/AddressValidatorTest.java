package guarantorsproject.com.codingassessment.validateaddress;

import guarantorsproject.com.codingassessment.validateaddress.controllers.AddressValidator;
import guarantorsproject.com.codingassessment.validateaddress.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AddressValidatorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AddressValidator addressValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidAddress_ReturnsValidStatus() {
        AddressRequest request = new AddressRequest();
        request.setInputAddress("1600 Amphitheatre Parkway, Mountain View, CA");

        CensusComponents comps = new CensusComponents();
        comps.setStreetName("Amphitheatre");
        comps.setStreetType("Parkway");
        comps.setCity("Mountain View");
        comps.setState("CA");
        comps.setZip("94043");

        CensusMatch match = new CensusMatch();
        match.setMatchedAddress("1600 AMPHITHEATRE PARKWAY, MOUNTAIN VIEW, CA, 94043");
        match.setAddressComponents(comps);

        CensusApiResponse response = new CensusApiResponse();
        CensusApiResponse.Result result = new CensusApiResponse.Result(); // ✅ correct
        result.setAddressMatches(Collections.singletonList(match));
        response.setResult(result);

        when(restTemplate.getForEntity(anyString(), eq(CensusApiResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // When
        ResponseEntity<AddressResponse> actualResponse = addressValidator.validate(request);

        // Then
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("valid", actualResponse.getBody().getStatus());
        assertEquals("MOUNTAIN VIEW", actualResponse.getBody().getCity());
    }

    @Test
    public void testCorrectedAddress_ReturnsCorrectedStatus() {
        AddressRequest request = new AddressRequest();
        request.setInputAddress("160 Amphiteatre Pkwy Mt View CA");

        CensusComponents comps = new CensusComponents();
        comps.setStreetName("Amphitheatre");
        comps.setStreetType("Parkway");
        comps.setCity("Mountain View");
        comps.setState("CA");
        comps.setZip("94043");

        CensusMatch match = new CensusMatch();
        match.setMatchedAddress("1600 AMPHITHEATRE PARKWAY, MOUNTAIN VIEW, CA, 94043");
        match.setAddressComponents(comps);

        CensusApiResponse response = new CensusApiResponse();
        CensusApiResponse.Result result = new CensusApiResponse.Result();
        result.setAddressMatches(Collections.singletonList(match));
        response.setResult(result);

        when(restTemplate.getForEntity(anyString(), eq(CensusApiResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        ResponseEntity<AddressResponse> actualResponse = addressValidator.validate(request);

        assertEquals("corrected", actualResponse.getBody().getStatus());
        assertNotNull(actualResponse.getBody().getMessage());
    }

    @Test
    public void testUnverifiableAddress_ReturnsUnverifiableStatus() {
        AddressRequest request = new AddressRequest();
        request.setInputAddress("Some Unknown Address");

        CensusApiResponse response = new CensusApiResponse();
        CensusApiResponse.Result result = new CensusApiResponse.Result();
        result.setAddressMatches(Collections.emptyList());
        response.setResult(result);

        when(restTemplate.getForEntity(anyString(), eq(CensusApiResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        ResponseEntity<AddressResponse> actualResponse = addressValidator.validate(request);

        assertEquals("unverifiable", actualResponse.getBody().getStatus());
        assertNotNull(actualResponse.getBody().getMessage());
    }

    @Test
    public void testEmptyInput_ReturnsBadRequest() {
        AddressRequest request = new AddressRequest();
        request.setInputAddress("   ");

        ResponseEntity<AddressResponse> actualResponse = addressValidator.validate(request);

        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertEquals("unverifiable", actualResponse.getBody().getStatus());
    }
}
