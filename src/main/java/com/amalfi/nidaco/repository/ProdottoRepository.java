package com.amalfi.nidaco.repository;

import com.amalfi.nidaco.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdottoRepository
        extends JpaRepository<Prodotto, Long> {


    @Query("""
            SELECT p
            FROM Prodotto p
            WHERE 
            (:filtro IS NULL OR :filtro = '')
            OR LOWER(p.codice) LIKE LOWER(CONCAT('%',:filtro,'%'))
            OR LOWER(p.barcode) LIKE LOWER(CONCAT('%',:filtro,'%'))
            OR LOWER(p.descrizione) LIKE LOWER(CONCAT('%',:filtro,'%'))
            ORDER BY p.descrizione
            """)
    List<Prodotto> search(String filtro);


}