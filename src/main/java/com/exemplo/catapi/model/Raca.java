package com.exemplo.catapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String origem;
    private String temperamento;
    private String descricao;

    @OneToMany(mappedBy = "raca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens;
}
