package com.atimera.eshop.backend.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Customer extends Person implements Cloneable {

    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipcode;
    private LocalDateTime registerOn;
    private String password;

}
