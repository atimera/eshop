package com.atimera.eshop.ui.view.product;


import com.atimera.eshop.backend.entity.Categorie;
import com.atimera.eshop.backend.entity.Produit;
import com.atimera.eshop.backend.service.CategorieService;
import com.atimera.eshop.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Produits | E-Shop")
@Route(value = "produits", layout = MainLayout.class)
public class ProductView extends VerticalLayout {

    private CategorieService categorieService;
    private final Grid<Produit> productGrid = new Grid<>(Produit.class);

    public ProductView(CategorieService categorieService) {
        addClassName("products-view");
        setSizeFull();

        configureProductsGrid();

        add(productGrid);
    }

    private void configureProductsGrid() {
        addClassName("product-grid");
        productGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        productGrid.removeColumnByKey("category");
        productGrid.setColumns("name");
        productGrid.addColumn(produit -> {
            Categorie category = produit.getCategory();
            return category == null ? "--" : produit.getCategory();
        }).setHeader("Catégorie");
        productGrid.getColumnByKey("name").setHeader("Nom produit");
    }
}
