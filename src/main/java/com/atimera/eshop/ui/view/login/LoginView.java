package com.atimera.eshop.ui.view.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle(value = "Login | E-Shop")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        login.setI18n(createFrenchI18n());



        add(new H1("E-Shop"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    // internationalisation du formulaire de login
    private LoginI18n createFrenchI18n() {
        final LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Se connecter");
        i18n.getHeader().setDescription("Description de l'application");
        i18n.getForm().setUsername("Identifiant");
        i18n.getForm().setTitle("Se connecter");
        i18n.getForm().setSubmit("Se connecter");
        i18n.getForm().setPassword("Mot de passe");
        i18n.getForm().setForgotPassword("Mot de passe oublié");
        i18n.getErrorMessage().setTitle("Identifiant ou mot de passe invalide");
        i18n.getErrorMessage()
                .setMessage("Vérifiez votre nom d'utilisateur et votre mot de passe et réessayez.");
        i18n.setAdditionalInformation("Si vous avez besoin de présenter des informations supplémentaires à " +
                "l'utilisateur (comme informations d'identification standard), c'est ici");
        return i18n;
    }
}
