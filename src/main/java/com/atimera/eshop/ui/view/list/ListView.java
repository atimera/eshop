package com.atimera.eshop.ui.view.list;

import com.atimera.eshop.backend.entity.Company;
import com.atimera.eshop.backend.entity.Contact;
import com.atimera.eshop.backend.service.CompanyService;
import com.atimera.eshop.backend.service.ContactService;
import com.atimera.eshop.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="", layout = MainLayout.class)
@PageTitle("Contacts | E-Shop")
public class ListView extends VerticalLayout {

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final ContactService contactService;
    private CompanyService companyService;
    private final TextField filterText = new TextField();
    private final ContactForm form;

    public ListView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;


        addClassName("list-view"); // class css pour la MainView
        setSizeFull(); // prend toute la largeur du navigateur
        configureGrid();

        getToolBar(); // Configure le champ de filtre

        form = new ContactForm(companyService.findAll()); // on initialise le formulaire
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form); // ajoute les 2 composants dans une div
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateListAndCloseEditor();
    }

    // Configure
    private void configureGrid() {
        grid.addClassName("contact-grid"); // class css pour la grid
        grid.setSizeFull();

        // On sp??cifie les colonnes de l'entit?? Contact qu'on veut afficher
        // Sinon il affiche toute les colonnes de Contact
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            // On formate la partie entreprise du contact si non null
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Entreprise");

        // Pour renommer les colonne en fran??ais ?? l'affichage
        grid.getColumnByKey("firstName").setHeader("Pr??nom");
        grid.getColumnByKey("lastName").setHeader("Nom");
        grid.getColumnByKey("status").setHeader("Statut");

        // Chaque colonne de la grille une largeur auto
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(
                event -> editContact(event.getValue()));
    }

    // Champ pour filtrer la liste par nom ou prenom
    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filtrer par nom...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Ajouter un contact", click -> addContact());

        HorizontalLayout toolBar = new HorizontalLayout(filterText, addContactButton);
        toolBar.addClassName("toolbar");

        return toolBar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    // ??diter un contact si s??lection
    private void editContact(Contact contact) {
        if(contact == null) {
            closeEditor(); // deselection => ferme l'??diteur
        } else {
            form.setVisible(true);
            form.setContact(contact); // pr??remplie le formulaire
            addClassName("editing"); // MainView Class
        }
    }

    // Ferme le formulaire
    private void closeEditor() {
        System.out.println("Fermeture de l'??diteur...");
        form.setContact(null); // vide le bean du formulaire
        form.setVisible(false); // rend le form invisible
        removeClassName("editing"); // enl??ve la class .edfiting ?? MainView
    }


    // R??cup??re la liste des contacts dans la base
    private void updateList() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

    // Suppression
    private void deleteContact(ContactForm.DeleteEvent evt) {
        contactService.delete(evt.getContact());
        updateListAndCloseEditor();
    }

    // met ?? jour la liste et ferme l'??diteur
    private void updateListAndCloseEditor() {
        updateList();
        closeEditor();
    }

    // Enregistrement en base puis fermeture
    private void saveContact(ContactForm.SaveEvent event) {
        contactService.save(event.getContact());
        updateListAndCloseEditor();
    }

}
