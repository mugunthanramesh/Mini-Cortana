package com.example.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "taxi_history")
public class UserHistory {
    private String taxiId;

    @Id
    private String bookingId;
    private String customerId;
    private String startingPoint;
    private String endingPoint;
    private Integer startTime;
    private Integer endTime;
    private Integer earnings;
    private Timestamp booked_at;

}
