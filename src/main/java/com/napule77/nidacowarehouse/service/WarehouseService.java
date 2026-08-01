package com.napule77.nidacowarehouse.service;

import com.napule77.nidacowarehouse.domain.MovementType;
import com.napule77.nidacowarehouse.domain.Product;
import com.napule77.nidacowarehouse.domain.StockMovement;
import com.napule77.nidacowarehouse.dto.CreateProductRequest;
import com.napule77.nidacowarehouse.dto.RegisterMovementRequest;
import com.napule77.nidacowarehouse.repository.ProductRepository;
import com.napule77.nidacowarehouse.repository.StockMovementRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public WarehouseService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(CreateProductRequest request) {
        productRepository.findBySku(request.sku()).ifPresent(product -> {
            throw new IllegalArgumentException("SKU già esistente");
        });

        Product product = new Product();
        product.setSku(request.sku());
        product.setName(request.name());
        product.setQuantity(request.quantity() == null ? 0 : request.quantity());
        product.setLocation(request.location());

        return productRepository.save(product);
    }

    @Transactional
    public StockMovement registerMovement(RegisterMovementRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato"));

        int current = product.getQuantity();
        int requested = request.quantity();
        int nextQuantity;

        if (request.type() == MovementType.OUTBOUND) {
            nextQuantity = current - requested;
            if (nextQuantity < 0) {
                throw new IllegalArgumentException("Quantità insufficiente a magazzino");
            }
        } else if (request.type() == MovementType.ADJUSTMENT) {
            nextQuantity = requested;
        } else {
            nextQuantity = current + requested;
        }

        product.setQuantity(nextQuantity);
        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(request.type());
        movement.setQuantity(requested);
        movement.setNote(request.note());

        return stockMovementRepository.save(movement);
    }
}
