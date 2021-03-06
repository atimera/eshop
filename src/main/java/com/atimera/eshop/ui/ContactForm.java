package com.atimera.eshop.ui;

import com.atimera.eshop.backend.entity.Company;
import com.atimera.eshop.backend.entity.Contact;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout {

    private Contact contact = new Contact();

    TextField firstName = new TextField("Prénom");
    TextField lastName = new TextField("Nom");
    EmailField email = new EmailField("Email");
    ComboBox<Contact.Status> status = new ComboBox<>("Statut");
    ComboBox<Company> company = new ComboBox<>("Entreprise");

    Button save = new Button("Sauvegarder");
    Button delete = new Button("Supprimer");
    Button close = new Button("Annuler");

    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactForm(List<Company> companies) {
        addClassName("contact-form");
        // Binging <nomChamp> <=> <nomPropriétéContact>
        binder.bindInstanceFields(this);

        // combo Entreprise:
        company.setItems(companies); // companies récupérées par un appel de service
        company.setItemLabelGenerator(Company::getName); // Construction des libellés: on prend les noms
        // Combo statut
        status.setItems(Contact.Status.values()); // Les statuts sont juste une liste énumérée: pas d'appel service

        // Ajout des champs au formulaire et des boutons
        add(
                firstName,
                lastName,
                email,
                company,
                status,
                createButtonsLayout() // crée les bouton Ajouter, Supprimer, Annuler
        );

    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER); // Possibilité d'ajouter en appuyant sur entrer
        close.addClickShortcut(Key.ESCAPE); // Possibilité de fermer/annuler la saisie form en appuyant sur échappe

        // Au click de Aouter: si valid Lance un  et SaveEvent avec le bean
        save.addClickListener(event -> validateAndSave());
        // Lance un DeleteEvent
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, contact)));
        // Lance un Close Event
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    /**
     * Remplie le bean avec les saisies du formulaire
     *
     * @param contact bean contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        // récupère les données saisie du form et les lie au bean contact
        binder.readBean(contact);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(contact);
            fireEvent(new SaveEvent(this, contact));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // ==============
    // === Events ===
    // ==============
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private final Contact contact;

        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            System.out.println("ContactFormEvent lancé...");
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    // Au clic sur Ajouter
    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
            System.out.println("SaveEvent lancé...");
        }
    }

    // Au clic sur Supprimer
    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
            System.out.println("DeleteEvent lancé...");
        }

    }

    // Au clic sur Annuler
    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
            System.out.println("CloseEvent lancé...");
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
