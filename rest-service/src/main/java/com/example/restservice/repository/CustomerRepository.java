package com.example.restservice.repository;

import com.example.restservice.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface CustomerRepository extends JpaRepository<CustomerDetails, String> {

    CustomerDetails findByEmail(String email);
}
