package com.atimera.eshop.backend.repository;

import com.atimera.eshop.backend.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    @Query("select c from Categorie c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Categorie> search(String searchTerm);
}
