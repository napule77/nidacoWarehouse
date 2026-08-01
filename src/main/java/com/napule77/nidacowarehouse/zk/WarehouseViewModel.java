package com.napule77.nidacowarehouse.zk;

import com.napule77.nidacowarehouse.domain.Product;
import com.napule77.nidacowarehouse.dto.CreateProductRequest;
import com.napule77.nidacowarehouse.service.WarehouseService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.List;

@VariableResolver(DelegatingVariableResolver.class)
public class WarehouseViewModel {

    @org.zkoss.zk.ui.select.annotation.WireVariable
    private WarehouseService warehouseService;

    private String sku;
    private String name;
    private Integer quantity = 0;
    private String location;
    private List<Product> products;

    @Init
    public void init() {
        refreshProducts();
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Command
    @NotifyChange({"products", "sku", "name", "quantity", "location"})
    public void addProduct() {
        warehouseService.createProduct(new CreateProductRequest(sku, name, quantity, location));
        sku = "";
        name = "";
        quantity = 0;
        location = "";
        refreshProducts();
    }

    private void refreshProducts() {
        products = warehouseService.listProducts();
    }
}
