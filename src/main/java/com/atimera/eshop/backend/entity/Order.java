package com.atimera.eshop.backend.entity;

import java.time.LocalDate;

public class Order {

    private Customer customer;
    private LocalDate date;
    private Float total;
    private String status;
    private String shippingAddress;
    private String paymentMethod;
    private String recipientName;
    private String recipientPhone;

}
