package com.example.restservice.controller;

import com.example.restservice.entity.UserHistory;
import com.example.restservice.repository.CustomerHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/userHistory")
public class CustomerHistoryController {

    @Autowired
    private CustomerHistory repository;

    public List<UserHistory> findAll() {
        return repository.findAll();
    }

    @RequestMapping(path = "/getHistory")
    public Iterable<UserHistory> getHistory(@RequestParam("customerId") String customerId) {

        List<UserHistory> historyList = new ArrayList<>();

        for(UserHistory userHistory : findAll()) {
            if(userHistory.getCustomerId().equals(customerId)) {
                historyList.add(userHistory);
            }
        }
        return historyList;
    }
}