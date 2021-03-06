package com.atimera.eshop.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Company extends AbstractEntity implements Cloneable{

    private String name;

    // Une entreprise a plusieurs contacts
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private final List<Contact> employees = new LinkedList<>();

    public Company() {
    }

    public Company(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getEmployees() {
        return employees;
    }

}
