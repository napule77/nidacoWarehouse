package com.napule77.nidacowarehouse.dto;

import com.napule77.nidacowarehouse.domain.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterMovementRequest(
        @NotNull Long productId,
        @NotNull MovementType type,
        @Min(1) Integer quantity,
        @Size(max = 250) String note
) {
}
