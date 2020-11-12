package com.example.restservice.repository;

import com.example.restservice.entity.TaxiDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface TaxiRepository extends JpaRepository<TaxiDetails, String> {

    TaxiDetails findByTaxiId(String id);
}
