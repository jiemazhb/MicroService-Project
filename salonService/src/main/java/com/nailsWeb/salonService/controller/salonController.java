package com.nailsWeb.salonService.controller;

import com.nailsWeb.salonService.model.Salon;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salons")
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
    @GetMapping("/salon/{id}/{username}")
    public Salon salonbyPathVariable(@PathVariable String id, @PathVariable("username") String name){
        Salon salon = new Salon();
        salon.setId(id);
        salon.setAddress("lambert ave");
        salon.setName(name);
        salon.setPhone(33234456);
        return salon;
    }
    @GetMapping("/userparams")
    public Salon salonByRequestParams(@RequestParam String id,
                                      @RequestParam("username") String name,
                                      @RequestParam(required = false, defaultValue = "this is default by Danny") String email){

    }
}
