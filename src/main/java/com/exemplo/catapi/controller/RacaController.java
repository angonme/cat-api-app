package com.exemplo.catapi.controller;

import com.exemplo.catapi.model.Raca;
import com.exemplo.catapi.service.RacaService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racas")
@RequiredArgsConstructor
public class RacaController {

    private final RacaService racaService;

    // a. Listar todas as raças
    @GetMapping
    @Timed(value = "raca.listarTodas", description = "Tempo para listar todas as raças")
    public ResponseEntity<List<Raca>> listarTodas() {
        return ResponseEntity.ok(racaService.listarTodas());
    }

    // b. Buscar informações de uma raça pelo ID
    @GetMapping("/{id}")
    @Timed(value = "raca.buscarPorId", description = "Tempo para buscar raça por ID")
    public ResponseEntity<Raca> buscarPorId(@PathVariable Long id) {
        Raca raca = racaService.buscarPorId(id);
        return (raca != null) ? ResponseEntity.ok(raca) : ResponseEntity.notFound().build();
    }

    // c. Buscar raças por temperamento
    @GetMapping("/temperamento/{temp}")
    @Timed(value = "raca.buscarPorTemperamento", description = "Tempo para buscar por temperamento")
    public ResponseEntity<List<Raca>> buscarPorTemperamento(@PathVariable String temp) {
        return ResponseEntity.ok(racaService.buscarPorTemperamento(temp));
    }

    // d. Buscar raças por origem
    @GetMapping("/origem/{origem}")
    @Timed(value = "raca.buscarPorOrigem", description = "Tempo para buscar por origem")
    public ResponseEntity<List<Raca>> buscarPorOrigem(@PathVariable String origem) {
        return ResponseEntity.ok(racaService.buscarPorOrigem(origem));
    }
}
