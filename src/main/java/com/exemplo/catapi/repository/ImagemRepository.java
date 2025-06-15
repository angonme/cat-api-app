package com.exemplo.catapi.repository;

import com.exemplo.catapi.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
