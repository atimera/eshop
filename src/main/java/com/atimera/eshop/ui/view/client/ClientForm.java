package com.atimera.eshop.ui.view.client;

import com.atimera.eshop.backend.entity.Client;
import com.atimera.eshop.backend.entity.Genre;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

@SuppressWarnings("unused")
public class ClientForm extends FormLayout {

    private Client client = new Client();

    Binder<Client> binder = new BeanValidationBinder<>(Client.class);

    TextField prenom = new TextField("Prénom");
    TextField nom = new TextField("Nom");
    EmailField email = new EmailField("Email");
    TextField telephone = new TextField("Téléphone");
    RadioButtonGroup<Genre> genre = new RadioButtonGroup<>();
    TextField adresse = new TextField("Adresse");
    IntegerField codePostal = new IntegerField("Code Postal");
    TextField ville = new TextField("Ville");
    TextField pays = new TextField("Pays");

    Button sauvegarder = new Button("Sauvegarder");
    Button supprimer = new Button("Supprimer");
    Button annuler = new Button("Annuler");


    public ClientForm() {
        addClassName("client-form");
        binder.bindInstanceFields(this);

        configureChamps();
        add(prenom, nom, email, telephone, genre, adresse, codePostal, ville, pays, creerBoutons());
    }

    private void configureChamps() {
        genre.setLabel("Genre");
        genre.setItems(Genre.values());
    }

    private HorizontalLayout creerBoutons() {
        sauvegarder.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        supprimer.addThemeVariants(ButtonVariant.LUMO_ERROR);
        annuler.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        sauvegarder.addClickShortcut(Key.ENTER);
        annuler.addClickShortcut(Key.ESCAPE);

        sauvegarder.addClickListener(event -> validerEtSauvegarderClient());
        supprimer.addClickListener(event -> fireEvent(new SupprimerEvent(this, client)));
        annuler.addClickListener(event -> fireEvent(new AnnulerEvent(this)));

        return new HorizontalLayout(sauvegarder, supprimer, annuler);
    }

    public void setContact(Client client) {
        this.client = client;
        binder.readBean(client);
    }

    private void validerEtSauvegarderClient() {
        System.out.println("Validation et sauvegarde...");
        try {
            binder.writeBean(client);
            fireEvent(new ClientForm.SauvegarderEvent(this, client));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void annuler() {
        System.out.println("Annulation...");
    }

    private void supprimerClient() {
        System.out.println("Suppression client...");
    }


    public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
        @Getter
        private final Client client;

        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         *  @param source     the source component
         *  param fromClient <code>true</code> if the event originated from the client
         * @param client Le bean Client
         */
        public ClientFormEvent(ClientForm source, Client client) {
            super(source, false);
            this.client = client;
        }
    }

    public static final class SauvegarderEvent extends ClientFormEvent{

        public SauvegarderEvent(ClientForm source, Client client) {
            super(source, client);
        }
    }

    public static final class SupprimerEvent extends ClientFormEvent{

        public SupprimerEvent(ClientForm source, Client client) {
            super(source, client);
        }
    }

    public static final class AnnulerEvent extends ClientFormEvent{

        public AnnulerEvent(ClientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
