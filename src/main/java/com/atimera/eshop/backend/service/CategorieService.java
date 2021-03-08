package com.atimera.eshop.backend.service;

import com.atimera.eshop.backend.entity.Categorie;
import com.atimera.eshop.backend.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategorieService {
    private static final Logger LOG = Logger.getLogger(CategorieService.class.getName());
    
    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public List<Categorie> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return categorieRepository.findAll();
        } else {
            return categorieRepository.search(stringFilter);
        }
    }


    public long count() {
        return categorieRepository.count();
    }

    public void delete(Categorie contact) {
        categorieRepository.delete(contact);
    }

    public void save(Categorie contact) {
        if (contact == null) {
            LOG.log(Level.SEVERE,
                    "Category est null. Êtes-vous sûrs d'avoir connecté votre formulaire à l'application ?");
            return;
        }
        categorieRepository.save(contact);
    }


    // ########### POST CONSTRUCT ################
    @PostConstruct
    public void populateTestData() {
        // Ajout des entreprises
        if (categorieRepository.count() == 0) {
            categorieRepository.saveAll(
                    Stream.of("Électronique", "Service", "Mobilier")
                            .map(Categorie::new)
                            .collect(Collectors.toList()));
        }
    }
}
