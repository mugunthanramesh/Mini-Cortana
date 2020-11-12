package com.example.restservice.bookingFunctions;

import com.example.restservice.entity.TaxiDetails;
import com.example.restservice.entity.UserHistory;
import com.example.restservice.repository.CustomerHistory;
import com.example.restservice.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class Booking {



    @Autowired
    private TaxiRepository taxiRepository;

    @Autowired
    private CustomerHistory customerHistory;

}

