package com.atimera.eshop.ui.view.client;

import com.atimera.eshop.backend.entity.Client;
import com.atimera.eshop.backend.service.ClientService;
import com.atimera.eshop.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle(value = "Clients | E-Sop")
@Route(value = "clients", layout = MainLayout.class)
public class ClientView extends VerticalLayout {

    private final Grid<Client> customerGrid = new Grid<>(Client.class);
    private final ClientService clientService;

    private final TextField filtreClient = new TextField();

    private final ClientForm form;

    public ClientView(ClientService clientService) {
        this.clientService = clientService;
        addClassName("customer-view");
        setSizeFull();

        form = new ClientForm();
        form.setVisible(false);
        form.addListener(ClientForm.SauvegarderEvent.class, this::sauvegarder);
        form.addListener(ClientForm.SupprimerEvent.class, this::supprimer);
        form.addListener(ClientForm.AnnulerEvent.class, e -> annulerSaisie());

        configureGrid();

        add(getToolBar(), form, customerGrid);
    }

    private HorizontalLayout getToolBar() {
        filtreClient.setPlaceholder("Prénom ou nom...");
        filtreClient.setClearButtonVisible(true);
        filtreClient.setValueChangeMode(ValueChangeMode.LAZY);
        filtreClient.addValueChangeListener(e -> mettreAJourList());

        Button ajouterClientBouton =
                new Button("Ajouter un client", click -> ajouterClient());

        HorizontalLayout toolBar = new HorizontalLayout(filtreClient, ajouterClientBouton);
        toolBar.addClassName("toolbar");

        return toolBar;
    }

    private void ajouterClient() {
        Client client = new Client();
        editerClient(client);
    }

    private void mettreAJourList() {
        customerGrid.setItems(clientService.findAll(filtreClient.getValue()));
    }

    private void sauvegarder(ClientForm.SauvegarderEvent event) {
        clientService.save(event.getClient());
        customerGrid.setItems(clientService.findAll());
        form.setVisible(false);
    }

    private void supprimer(ClientForm.SupprimerEvent event) {
        System.out.println("Suppression client...");
        clientService.delete(event.getClient());
        customerGrid.setItems(clientService.findAll());
    }

    private void annulerSaisie() {
        form.setContact(null);
        form.setVisible(false);
    }



    private void configureGrid() {
        addClassName("customer-grid");
        customerGrid.setColumns(
                "nom", "prenom", "genre", "email", "telephone",
                "adresse", "ville", "pays", "codePostal", "dateInscription", "motDePasse");
        customerGrid.getColumns().forEach(cc -> cc.setAutoWidth(true));
        customerGrid.setItems(clientService.findAll());

        customerGrid.asSingleSelect().addValueChangeListener(
                event -> editerClient(event.getValue()));
    }

    private void editerClient(Client client) {
        if(client == null) {
            annulerSaisie();
        } else {
            form.setVisible(true);
            form.setContact(client);
        }
    }
}
