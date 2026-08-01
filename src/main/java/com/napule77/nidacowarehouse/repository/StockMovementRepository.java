package com.napule77.nidacowarehouse.repository;

import com.napule77.nidacowarehouse.domain.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}
