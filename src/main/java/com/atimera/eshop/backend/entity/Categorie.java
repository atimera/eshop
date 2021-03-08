package com.atimera.eshop.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Categorie extends AbstractEntity implements Cloneable {

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private final List<Produit> produits = new LinkedList<>();


    public Categorie() {
    }

    public Categorie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Produit> getProducts() {
        return produits;
    }
}
