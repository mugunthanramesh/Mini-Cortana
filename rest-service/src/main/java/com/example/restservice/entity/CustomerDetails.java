package com.example.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_data")
public class CustomerDetails {

    @Id
    private String email;
    private String firstname;
    private String lastname;
    private String customerId;
    private String privilege;
    private String password;

    public String getUsername() {
        return this.firstname + " " + this.lastname;
    }
}
