package guarantorsproject.com.example.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LandingPoint {
    @GetMapping("/form")
    public String showForm() {
        return "address-form";
    }

//    @PostMapping("/submit-address")
    public String submitAddress(@RequestParam String address, Model model) {
        System.out.println("Submitted address: " + address); System.out.println("Submitted address: " + address);
        model.addAttribute("submittedAddress", address);
        return "address-form";
    }
}
