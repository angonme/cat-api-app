package com.exemplo.catapi.repository;

import com.exemplo.catapi.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    // Métodos personalizados podem ser adicionados se necessário
}
