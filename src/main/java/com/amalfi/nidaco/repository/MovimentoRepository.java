package com.amalfi.nidaco.repository;

import com.amalfi.nidaco.entity.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoRepository
        extends JpaRepository<Movimento,Long> {

}