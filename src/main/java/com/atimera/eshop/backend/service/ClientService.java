package com.atimera.eshop.backend.service;

import com.atimera.eshop.backend.entity.Client;
import com.atimera.eshop.backend.entity.Genre;
import com.atimera.eshop.backend.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientService {
    private static final Logger LOG = Logger.getLogger(ClientService.class.getName());

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<Client> findAll() {
        return  clientRepository.findAll();
    }

    public List<Client> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return  clientRepository.findAll();
        } else {
            return  clientRepository.search(stringFilter);
        }
    }


    public long count() {
        return  clientRepository.count();
    }

    public void delete(Client client) {
         clientRepository.delete(client);
    }

    public void save(Client contact) {
        if (contact == null) {
            LOG.log(Level.SEVERE,
                    "Client est null. Êtes-vous sûrs d'avoir connecté votre formulaire à l'application ?");
            return;
        }
         clientRepository.save(contact);
    }


    // ########### POST CONSTRUCT ################
    @PostConstruct
    public void populateTestData() {
        // Ajout des entreprises
        if ( clientRepository.count() == 0) {
             clientRepository.saveAll(
                     Stream.of("Amdiatou Timera", "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                             "Koen Johansen", "Alejandro Macdonald")
                             .map(name -> {
                                 String[] split = name.split(" ");
                                 Client client = new Client();
                                 client.setPrenom(split[0]);
                                 client.setNom(split[1]);
                                 String email = (client.getPrenom() + "." + client.getNom() + "@gmail.com").toLowerCase();
                                 client.setEmail(email);
                                 client.setAdresse("1 Rue Camille");
                                 client.setVille("Paris");
                                 client.setCodePostal(75013);
                                 client.setPays("France");
                                 client.setDateInscription(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
                                 client.setGenre(Genre.Autre);
                                 client.setMotDePasse("password");
                                 client.setTelephone("0754568456");

                                 return client;
                             }).collect(Collectors.toList()));
        }
    }


}
