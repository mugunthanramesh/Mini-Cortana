package com.example.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "taxi_data")
public class TaxiDetails {

    @Id
    private String taxiId;
    private String taxiName;
    private Integer totalEarnings;
    private String availablePoint;
    private Integer numberOfBookings;
}
