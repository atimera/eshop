package com.atimera.eshop.backend.service;

import com.atimera.eshop.backend.entity.Company;
import com.atimera.eshop.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();

        // pour chaque entreprise on récupere le nombre d'employés
        findAll().forEach(company ->
                stats.put(company.getName(), company.getEmployees().size()));

        return stats;
    }

}
