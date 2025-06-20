package com.exemplo.catapi.repository;

import com.exemplo.catapi.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {

    // Buscar raças por temperamento (ignora maiúsculas/minúsculas)
    List<Raca> findByTemperamentoContainingIgnoreCase(String temperamento);

    // Buscar raças por origem (ignora maiúsculas/minúsculas)
    List<Raca> findByOrigemContainingIgnoreCase(String origem);
}
