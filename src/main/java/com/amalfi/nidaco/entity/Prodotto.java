package com.amalfi.nidaco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="prodotti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prodotto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true)
    private String codice;

    private String barcode;

    @Column(nullable=false)
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    private String unitaMisura;

    private BigDecimal prezzoAcquisto;

    private BigDecimal prezzoVendita;

    private BigDecimal iva;

    private Integer giacenza;

    private Integer scortaMinima;

    private Boolean attivo;

}