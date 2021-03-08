package com.atimera.eshop.backend.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Produit extends AbstractEntity implements Cloneable {

    private String name;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Categorie category;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categorie getCategory() {
        return category;
    }

    public void setCategory(Categorie category) {
        this.category = category;
    }
}
