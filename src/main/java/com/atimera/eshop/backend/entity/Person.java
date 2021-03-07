package com.atimera.eshop.backend.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person extends AbstractEntity {

    private String firstName;
    private String lastName;

}
