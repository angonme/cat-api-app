package com.exemplo.catapi.repository;

import com.exemplo.catapi.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RacaRepository extends JpaRepository<Raca, String> {
    List<Raca> findByTemperamentoContainingIgnoreCase(String temperamento);
    List<Raca> findByOrigemContainingIgnoreCase(String origem);
}
