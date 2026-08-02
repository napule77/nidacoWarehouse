package com.amalfi.nidaco.repository;

import com.amalfi.nidaco.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}