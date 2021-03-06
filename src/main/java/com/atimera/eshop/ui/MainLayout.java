package com.atimera.eshop.ui;

import com.atimera.eshop.ui.view.dashboard.DashboardView;
import com.atimera.eshop.ui.view.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


@CssImport("./styles/shared-styles.css") // Chemin relatif au dossier frontend <=> ./
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader(); // le header
        createDrawer(); // le drawer = menu coté = tiroir
    }

    private void createHeader() {
        H1 logo = new H1("E-Shop");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER); // Composant flex centré
        header.setWidth("100%");
        header.addClassName("header");


        addToNavbar(header);

    }

    private void createDrawer() {
        // La route
        RouterLink listLink = new RouterLink("Liste", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        // Ajout des routes au drawer
        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("Tableau de bord", DashboardView.class)
        ));
    }
}
