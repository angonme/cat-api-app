package com.exemplo.catapi.model;

import jakarta.persistence.*;

@Entity
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "raca_id")
    private Raca raca;

    // Getters e setters
}
