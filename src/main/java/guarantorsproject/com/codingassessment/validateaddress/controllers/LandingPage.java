package guarantorsproject.com.codingassessment.validateaddress.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPage {
    @GetMapping("/form")
    public String showForm() {
        return "address-form";
    }
}
