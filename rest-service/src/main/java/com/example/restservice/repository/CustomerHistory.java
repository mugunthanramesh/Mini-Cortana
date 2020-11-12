package com.example.restservice.repository;

import com.example.restservice.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface CustomerHistory extends JpaRepository<UserHistory, Integer> {

}
