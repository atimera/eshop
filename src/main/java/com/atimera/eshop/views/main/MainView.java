package com.atimera.eshop.views.main;

import com.atimera.eshop.backend.entity.Company;
import com.atimera.eshop.backend.entity.Contact;
import com.atimera.eshop.backend.service.ContactService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final ContactService contactService;

    public MainView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view"); // class css pour la MainView
        setSizeFull(); // prend toute la largeur du navigateur
        configureGrid();
        updateList();

        add(grid);
    }

    // Configure
    private void configureGrid() {
        grid.addClassName("contact-grid"); // class css pour la grid
        grid.setSizeFull();

        // On spécifie les colonnes de l'entité Contact qu'on veut afficher
        // Sinon il affiche toute les colonnes de Contact
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            // On formate la partie entreprise du contact si non null
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Entreprise");

        // Pour renommer les colonne en français à l'affichage
        grid.getColumnByKey("firstName").setHeader("Prénom");
        grid.getColumnByKey("lastName").setHeader("Nom");
        grid.getColumnByKey("status").setHeader("Statut");

        // Chaque colonne de la grille une largeur auto
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    // Récupère la liste des contacts dans la base
    private void updateList() {
        grid.setItems(contactService.findAll());
    }

}
