package com.atimera.eshop.ui.view.product;


import com.atimera.eshop.backend.entity.Category;
import com.atimera.eshop.backend.entity.Product;
import com.atimera.eshop.backend.service.CategoryService;
import com.atimera.eshop.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Produits | E-Shop")
@Route(value = "produits", layout = MainLayout.class)
public class ProductView extends VerticalLayout {

    private CategoryService categoryService;
    private final Grid<Product> productGrid = new Grid<>(Product.class);

    public ProductView(CategoryService categoryService) {
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
        productGrid.addColumn(product -> {
            Category category = product.getCategory();
            return category == null ? "--" : product.getCategory();
        }).setHeader("Catégorie");
        productGrid.getColumnByKey("name").setHeader("Nom produit");
    }
}
