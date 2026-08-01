package com.napule77.nidacowarehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank @Size(max = 40) String sku,
        @NotBlank @Size(max = 120) String name,
        @Min(0) Integer quantity,
        @Size(max = 80) String location
) {
}
