package com.example.restservice.bookingFunctions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BookingForm {
    private String email;
    private String startingPoint;
    private Integer startTime;
    private String endingPoint;
    private String customerId;
}
