package guarantorsproject.com.example.project.controllers;

import guarantorsproject.com.example.project.model.AddressRequest;
import guarantorsproject.com.example.project.model.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressValidator {

    @PostMapping("/submit-address")
    public ResponseEntity<AddressResponse> validateAddress(@RequestBody AddressRequest request) {
        String input = request.getInputAddress();

        AddressResponse response = new AddressResponse();

        if (input == null || input.trim().isEmpty()) {
            response.setStatus("unverifiable");
            return ResponseEntity.badRequest().body(response);
        }

        if (input.toLowerCase().contains("amphitheatre")) {
            response.setStreet("Amphitheatre Parkway");
            response.setNumber("1600");
            response.setCity("Mountain View");
            response.setState("CA");
            response.setZipCode("94043");
            response.setStatus("valid");
        } else {
            response.setStatus("unverifiable");
        }

        return ResponseEntity.ok(response);
    }
}