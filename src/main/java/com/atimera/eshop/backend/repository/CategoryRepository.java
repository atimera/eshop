package com.atimera.eshop.backend.repository;

import com.atimera.eshop.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<Category> search(String searchTerm);
}
