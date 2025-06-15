package com.exemplo.catapi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Raca {
    @Id
    private String id;
    private String nome;
    private String origem;
    private String temperamento;
    private String descricao;

    @OneToMany(mappedBy = "raca", cascade = CascadeType.ALL)
    private List<Imagem> imagens;

    // Getters e setters
}
