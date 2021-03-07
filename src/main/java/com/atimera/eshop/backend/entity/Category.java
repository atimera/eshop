package com.atimera.eshop.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Category extends AbstractEntity implements Cloneable {

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private final List<Product> products = new LinkedList<>();


    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }
}
