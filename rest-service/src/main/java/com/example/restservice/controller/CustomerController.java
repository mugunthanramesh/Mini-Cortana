package com.example.restservice.controller;

import com.example.restservice.entity.CustomerDetails;
import com.example.restservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping
    public Iterable<CustomerDetails> findAll() {
        return repository.findAll();
    }

    @RequestMapping(path = "/email")
    public CustomerDetails find(@RequestParam("email") String email) {
        return this.repository.findByEmail(email);
    }

    @RequestMapping(path = "/getDetails")
    public CustomerDetails getDetails(@RequestParam("username") String username, @RequestParam("pswd") String password) {
        for(CustomerDetails customerDetails : findAll()) {
            if(customerDetails.getUsername().equals(username) && customerDetails.getPassword().equals(password)) {
                return customerDetails;
            }
        }
        return null;
    }

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public void createCustomer(@RequestBody CustomerDetails customerDetails) {
        customerDetails.setCustomerId(UUID.randomUUID().toString());
        repository.save(customerDetails);
    }

    @RequestMapping(path = "/isUserPresent")
    public String isUserPresent(@RequestParam("email")String email) {
        CustomerDetails customerDetails = find(email);
        return customerDetails == null ? null : customerDetails.getUsername();
    }

    @RequestMapping(path = "/passwordMatch", method = RequestMethod.GET)
    public String matchPassword(@RequestParam("password") String pswd, @RequestParam("email") String email) {
        if(find(email).getPassword().equals(pswd)) {
            return find(email).getPrivilege();
        }
        else {
            return null;
        }
    }
}
