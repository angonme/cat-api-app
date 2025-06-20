package com.exemplo.catapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String origem;

    private String temperamento;

    @Column(length = 1000) // <-- aumentamos o limite aqui
    private String descricao;
}
