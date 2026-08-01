package com.napule77.nidacowarehouse.controller;

import com.napule77.nidacowarehouse.domain.Product;
import com.napule77.nidacowarehouse.domain.StockMovement;
import com.napule77.nidacowarehouse.dto.CreateProductRequest;
import com.napule77.nidacowarehouse.dto.RegisterMovementRequest;
import com.napule77.nidacowarehouse.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/products")
    public List<Product> products() {
        return warehouseService.listProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.createProduct(request));
    }

    @PostMapping("/movements")
    public ResponseEntity<StockMovement> registerMovement(@RequestBody @Valid RegisterMovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.registerMovement(request));
    }
}
