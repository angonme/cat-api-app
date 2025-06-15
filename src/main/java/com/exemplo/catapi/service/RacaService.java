package com.exemplo.catapi.service;

import com.exemplo.catapi.model.Raca;
import com.exemplo.catapi.repository.RacaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacaService {
    private final RacaRepository racaRepository;

    public RacaService(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public List<Raca> listarTodas() {
        return racaRepository.findAll();
    }

    public Raca buscarPorId(String id) {
        return racaRepository.findById(id).orElse(null);
    }

    public List<Raca> buscarPorTemperamento(String temp) {
        return racaRepository.findByTemperamentoContainingIgnoreCase(temp);
    }

    public List<Raca> buscarPorOrigem(String origem) {
        return racaRepository.findByOrigemContainingIgnoreCase(origem);
    }
}
