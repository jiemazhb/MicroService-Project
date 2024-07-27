package com.nailsWeb.salonService.controller;

import com.nailsWeb.salonService.model.Salon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class salonController {
    @RequestMapping("/")
    public String home(){
        return "testing by Danny";
    }
    @GetMapping("/salon")
    public Salon getSalon(){
        Salon salon = new Salon();
        salon.setId("1");
        salon.setAddress("lambert ave");
        salon.setName("topline");
        salon.setPhone(33234456);
        return salon;
    }
}
