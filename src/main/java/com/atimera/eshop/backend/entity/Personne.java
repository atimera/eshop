package com.atimera.eshop.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class Personne extends AbstractEntity {

    private String prenom;
    private String nom;
    private Genre genre;


}
