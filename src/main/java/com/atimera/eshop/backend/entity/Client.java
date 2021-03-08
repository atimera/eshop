package com.atimera.eshop.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Client extends Personne implements Cloneable {

    private String email;
    private String telephone;
    private String adresse;
    private String ville;
    private String pays;
    private Integer codePostal;
    private String dateInscription;
    private String motDePasse;

    public Client() {
    }


}
