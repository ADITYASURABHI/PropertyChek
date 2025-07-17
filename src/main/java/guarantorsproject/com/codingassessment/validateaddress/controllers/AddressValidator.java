package guarantorsproject.com.codingassessment.validateaddress.controllers;

import guarantorsproject.com.codingassessment.validateaddress.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RestController
public class AddressValidator {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/validate-address")
    public ResponseEntity<AddressResponse> validate(@RequestBody AddressRequest request) {
        AddressResponse response = new AddressResponse();
        String input = request.getInputAddress();

        if (input == null || input.trim().isEmpty()) {
            response.setStatus("unverifiable");
            response.setMessage("The address you entered could not be verified.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String encoded = UriUtils.encode(input, StandardCharsets.UTF_8);
            String url = "https://geocoding.geo.census.gov/geocoder/locations/onelineaddress"
                    + "?address=" + encoded
                    + "&benchmark=2020&format=json";

            ResponseEntity<CensusApiResponse> censusResponse = restTemplate.getForEntity(url, CensusApiResponse.class);
            CensusApiResponse result = censusResponse.getBody();

            if (result != null && !result.getResult().getAddressMatches().isEmpty()) {
                CensusMatch match = result.getResult().getAddressMatches().get(0);
                CensusComponents comps = match.getAddressComponents();

                String matchedStreet = comps.getStreetName() +
                        (comps.getStreetType() != null ? " " + comps.getStreetType() : "");
                String matchedCity = comps.getCity();
                String matchedState = comps.getState();

                String normalizedInput = input.toLowerCase().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ");
                String normalizedStreet = matchedStreet.toLowerCase().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ");
                String normalizedCity = matchedCity.toLowerCase().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ");
                String normalizedState = matchedState.toLowerCase().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ");

                boolean isStreetOk = normalizedInput.contains(normalizedStreet);
                boolean isCityOk = normalizedInput.contains(normalizedCity);
                boolean isStateOk = normalizedInput.contains(normalizedState);

                boolean isCorrected = !(isStreetOk && isCityOk && isStateOk);

                response.setStreet(match.getMatchedAddress());
                response.setCity(comps.getCity());
                response.setState(comps.getState());
                response.setZipCode(comps.getZip());
                response.setStatus(isCorrected ? "corrected" : "valid");

                if (isCorrected) {
                    response.setMessage("We corrected the address you submitted.");
                }

            } else {
                response.setStatus("unverifiable");
                response.setMessage("The address you entered could not be verified.");
            }

        } catch (Exception e) {
            response.setStatus("unverifiable");
            response.setMessage("The address you entered could not be verified.");
        }

        return ResponseEntity.ok(response);
    }
}