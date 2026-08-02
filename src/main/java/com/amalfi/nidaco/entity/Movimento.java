package com.amalfi.nidaco.entity;

import com.amalfi.nidaco.enumerator.TipoMovimento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "movimenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prodotto_id")
    private Prodotto prodotto;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TipoMovimento tipo;


    private Integer quantita;


    private LocalDateTime dataMovimento;


    private String note;


}