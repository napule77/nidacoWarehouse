package com.napule77.nidacowarehouse.controller;

import com.napule77.nidacowarehouse.domain.MovementType;
import com.napule77.nidacowarehouse.domain.Product;
import com.napule77.nidacowarehouse.domain.StockMovement;
import com.napule77.nidacowarehouse.exception.GlobalExceptionHandler;
import com.napule77.nidacowarehouse.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WarehouseController.class)
@Import(GlobalExceptionHandler.class)
@org.springframework.test.context.TestPropertySource(properties = {
        "spring.mvc.throw-exception-if-no-handler-found=true",
        "spring.web.resources.add-mappings=false"
})
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseService warehouseService;

    // ── GET /api/warehouse/products ───────────────────────────────────────────

    @Test
    void listProducts_returns200WithEmptyList() throws Exception {
        when(warehouseService.listProducts()).thenReturn(List.of());

        mockMvc.perform(get("/api/warehouse/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void listProducts_returns200WithProducts() throws Exception {
        Product p = new Product();
        p.setSku("SKU-001");
        p.setName("Bulloneria M8");
        p.setQuantity(120);
        p.setLocation("A1-03");
        when(warehouseService.listProducts()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/warehouse/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("SKU-001"))
                .andExpect(jsonPath("$[0].name").value("Bulloneria M8"));
    }

    // ── POST /api/warehouse/products ──────────────────────────────────────────

    @Test
    void createProduct_withValidBody_returns201() throws Exception {
        Product saved = new Product();
        saved.setSku("SKU-002");
        saved.setName("Vite M6");
        saved.setQuantity(50);
        when(warehouseService.createProduct(any())).thenReturn(saved);

        String body = """
                {"sku":"SKU-002","name":"Vite M6","quantity":50,"location":"B2-01"}
                """;

        mockMvc.perform(post("/api/warehouse/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("SKU-002"));
    }

    @Test
    void createProduct_withMissingSku_returns400() throws Exception {
        String body = """
                {"name":"Vite M6","quantity":50}
                """;

        mockMvc.perform(post("/api/warehouse/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    void createProduct_withDuplicateSku_returns400() throws Exception {
        when(warehouseService.createProduct(any()))
                .thenThrow(new IllegalArgumentException("SKU già esistente"));

        String body = """
                {"sku":"SKU-001","name":"Duplicate","quantity":10}
                """;

        mockMvc.perform(post("/api/warehouse/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("SKU già esistente"));
    }

    // ── POST /api/warehouse/movements ─────────────────────────────────────────

    @Test
    void registerMovement_withValidBody_returns201() throws Exception {
        StockMovement movement = new StockMovement();
        movement.setType(MovementType.INBOUND);
        movement.setQuantity(20);
        when(warehouseService.registerMovement(any())).thenReturn(movement);

        String body = """
                {"productId":1,"type":"INBOUND","quantity":20,"note":"test"}
                """;

        mockMvc.perform(post("/api/warehouse/movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void registerMovement_withInsufficientStock_returns400() throws Exception {
        when(warehouseService.registerMovement(any()))
                .thenThrow(new IllegalArgumentException("Quantità insufficiente a magazzino"));

        String body = """
                {"productId":1,"type":"OUTBOUND","quantity":9999,"note":"test"}
                """;

        mockMvc.perform(post("/api/warehouse/movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Quantità insufficiente a magazzino"));
    }

    // ── 404 for unknown REST paths ─────────────────────────────────────────────

    @Test
    void unknownApiPath_returns404WithStructuredError() throws Exception {
        // Spring Boot 3 returns RFC 7807 Problem Details for unknown paths —
        // a structured, standard JSON format that does not leak internal details.
        mockMvc.perform(get("/api/unknown/path"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Not Found"));
    }
}
