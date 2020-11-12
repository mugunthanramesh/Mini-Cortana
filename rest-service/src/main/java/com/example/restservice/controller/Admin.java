package com.example.restservice.controller;

import com.example.restservice.entity.TaxiDetails;
import com.example.restservice.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class Admin {

    @Autowired
    private TaxiRepository taxiRepository;

    @RequestMapping(path = "/addTaxi")
    public boolean addTaxi(@RequestParam("taxiname") String taxiName) {
        taxiRepository.save(new TaxiDetails(UUID.randomUUID().toString(), taxiName,0,"A",0));
        return true;
    }

    @RequestMapping("/view")
    List<TaxiDetails> viewAll() {
        return taxiRepository.findAll();
    }

}
