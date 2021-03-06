package com.atimera.eshop.backend.repository;

import com.atimera.eshop.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
