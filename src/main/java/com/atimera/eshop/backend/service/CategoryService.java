package com.atimera.eshop.backend.service;

import com.atimera.eshop.backend.entity.Category;
import com.atimera.eshop.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryService {
    private static final Logger LOG = Logger.getLogger(CategoryService.class.getName());
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return categoryRepository.findAll();
        } else {
            return categoryRepository.search(stringFilter);
        }
    }


    public long count() {
        return categoryRepository.count();
    }

    public void delete(Category contact) {
        categoryRepository.delete(contact);
    }

    public void save(Category contact) {
        if (contact == null) {
            LOG.log(Level.SEVERE,
                    "Category est null. Êtes-vous sûrs d'avoir connecté votre formulaire à l'application ?");
            return;
        }
        categoryRepository.save(contact);
    }


    // ########### POST CONSTRUCT ################
    @PostConstruct
    public void populateTestData() {
        // Ajout des entreprises
        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(
                    Stream.of("Électronique", "Service", "Mobilier")
                            .map(Category::new)
                            .collect(Collectors.toList()));
        }
    }
}
