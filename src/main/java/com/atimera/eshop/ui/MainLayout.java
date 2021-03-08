package com.atimera.eshop.ui;

import com.atimera.eshop.ui.view.client.ClientView;
import com.atimera.eshop.ui.view.dashboard.DashboardView;
import com.atimera.eshop.ui.view.list.ListView;
import com.atimera.eshop.ui.view.product.ProductView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;


@PWA(
        name = "E-Shop",
        shortName = "eShop",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png"},
        iconPath = "./icons/icon.png",
        offlinePath = "./offline.html"
)
@CssImport("./styles/shared-styles.css") // Chemin relatif au dossier frontend <=> ./
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader(); // le header
        createDrawer(); // le drawer = menu coté = tiroir
    }

    private void createHeader() {
        H1 logo = new H1("E-Shop");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Se déconnecter");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER); // Composant flex centré
        header.setWidth("100%");
        header.addClassName("header");


        addToNavbar(header);

    }

    private void createDrawer() {

        // Liste des contacts
        RouterLink listContactsLink = new RouterLink("Liste contacts", ListView.class);
        listContactsLink.setHighlightCondition(HighlightConditions.sameLocation());

        // Ajout des routes au drawer
        addToDrawer(new VerticalLayout(
                new RouterLink("Clients", ClientView.class),
                new RouterLink("Produits", ProductView.class),
                listContactsLink,
                new RouterLink("Tableau de bord", DashboardView.class)
        ));
    }

}
