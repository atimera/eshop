package com.atimera.eshop.ui;

import com.atimera.eshop.backend.entity.Company;
import com.atimera.eshop.backend.entity.Contact;
import com.atimera.eshop.backend.service.CompanyService;
import com.atimera.eshop.backend.service.ContactService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/shared-styles.css") // Chemin relatif au dossier frontend <=> ./
public class MainView extends VerticalLayout {

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final ContactService contactService;
    private final CompanyService companyService;
    private final TextField filterText = new TextField();
    private final ContactForm form;

    public MainView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        this.companyService = companyService;

        addClassName("list-view"); // class css pour la MainView
        setSizeFull(); // prend toute la largeur du navigateur

        configureFilter(); // Configure le champ de filtre
        configureGrid();

        form = new ContactForm(companyService.findAll()); // on initialise le formulaire

        Div content = new Div(grid, form); // ajoute les 2 composants dans une div
        content.addClassName("content");
        content.setSizeFull();

        // Ajoute le champ juste avant les autres composants.
        // et comme on est dans un VerticalLayout, le champ sera au dessus
        add(filterText, content);
        updateList();
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

    // Champ pour filtrer la liste par nom ou prenom
    private void configureFilter() {
        filterText.setPlaceholder("Filtrer par nom...");
        filterText.setClearButtonVisible(true);

        // évite de lancer un event à chaque value change
        // attend un court instant après la saisie avant de lancer
        // l'action à faire c-à-d l'appel à la bdd
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        // Met à jour la requête
        filterText.addValueChangeListener(e -> updateList());
    }

    // Récupère la liste des contacts dans la base
    private void updateList() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

}
