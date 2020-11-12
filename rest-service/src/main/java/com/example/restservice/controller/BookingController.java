package com.example.restservice.controller;


import com.example.restservice.bookingFunctions.Booking;
import com.example.restservice.bookingFunctions.BookingForm;
import com.example.restservice.entity.TaxiDetails;
import com.example.restservice.entity.UserHistory;
import com.example.restservice.repository.CustomerHistory;
import com.example.restservice.repository.TaxiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class BookingController {

    @Autowired
    private TaxiRepository taxiRepository;

    @Autowired
    private CustomerHistory customerHistory;

    private List<String> bookingPoints = Arrays.asList("A","B","C","D","E","F");

    @RequestMapping(path = "/booking")
    public boolean makeBooking(@RequestParam("email") String email, @RequestParam("startingPoint") String startingPoint,
                               @RequestParam("endingPoint") String endingPoint, @RequestParam("customerId") String customerId,
                               @RequestParam("startTime") Integer startTime) {
        BookingForm bookingForm = new BookingForm(email, startingPoint, startTime, endingPoint, customerId);
        return bookTaxi(bookingForm);
    }

    public boolean bookTaxi(BookingForm bookingForm) {

        Integer startingPoint = bookingPoints.indexOf(bookingForm.getStartingPoint());
        Integer endingPoint = bookingPoints.indexOf(bookingForm.getEndingPoint());


        Integer endTime = ((endingPoint - startingPoint) + bookingForm.getStartTime()) % 24;
        String freeTaxi = availableTaxi(bookingForm.getStartTime(), endTime, startingPoint);

        if(freeTaxi == null) {
            return false;
        }
        Integer computePrice = computePrice(startingPoint, endingPoint);
        customerHistory.save(new UserHistory(freeTaxi,UUID.randomUUID().toString(),bookingForm.getCustomerId(),
                bookingForm.getStartingPoint(), bookingForm.getEndingPoint(), bookingForm.getStartTime(), endTime, computePrice,
                Timestamp.from(Instant.now())));
        TaxiDetails taxiDetails = taxiRepository.findByTaxiId(freeTaxi);
        taxiDetails.setAvailablePoint(bookingForm.getEndingPoint());
        taxiDetails.setTotalEarnings(taxiDetails.getTotalEarnings() + computePrice);
        taxiDetails.setNumberOfBookings(taxiDetails.getNumberOfBookings() + 1);
        taxiRepository.save(taxiDetails);
        return true;
    }

    private Integer computePrice(Integer startPoint, Integer EndPoint) {
        final Integer kmDifference = 15;
        final Integer minimumPrice = 100;
        final Integer minimumKM = 5;
        final Integer subsequentPrice = 10;

        Integer distanceTravel = kmDifference * (EndPoint - startPoint);
        Integer Price = minimumPrice ;

        if(distanceTravel <= minimumKM) {
            return Price;
        }
        else {
            Price += (distanceTravel - minimumKM) * subsequentPrice;
            return Price;
        }
    }

    private String availableTaxi(Integer startingTime, Integer endingTime, Integer startPoint) {

        List<TaxiDetails> taxiDetails = taxiRepository.findAll();
        List<UserHistory> userHistories = customerHistory.findAll();

        List<TaxiDetails> isFreeList = new ArrayList<>();



        for(TaxiDetails details : taxiDetails) {
            Boolean flag = true;
            for(UserHistory userHistory : userHistories) {
                if(userHistory.getTaxiId().equals(details.getTaxiId()) && !((startingTime < userHistory.getStartTime() && endingTime <= userHistory.getStartTime())
                || (startingTime >= userHistory.getEndTime() && endingTime > userHistory.getEndTime()))) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                isFreeList.add(details);
            }
        }

        log.info(isFreeList.toString());

        if(isFreeList.isEmpty()) {
            return null;
        }
        else if (isFreeList.size() == 1) {
            return isFreeList.get(0).getTaxiId();
        }

        Integer min = Math.abs(bookingPoints.indexOf(isFreeList.get(0).getAvailablePoint()) - startPoint);
        String minTaxiId = isFreeList.get(0).getTaxiId();
        Integer minEarnings = isFreeList.get(0).getTotalEarnings();

        for(int i = 1; i < isFreeList.size(); i++) {
            if(Math.abs(bookingPoints.indexOf(isFreeList.get(i).getAvailablePoint()) - startPoint) == min &&
                    minEarnings > isFreeList.get(i).getTotalEarnings() ||
                    Math.abs(bookingPoints.indexOf(isFreeList.get(i).getAvailablePoint()) - startPoint) < min) {
                min = Math.abs(bookingPoints.indexOf(isFreeList.get(i).getAvailablePoint()) - startPoint);
                minTaxiId = isFreeList.get(i).getTaxiId();
                minEarnings = isFreeList.get(i).getTotalEarnings();
            }
        }
        return minTaxiId;
    }

}
