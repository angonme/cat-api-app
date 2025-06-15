package com.exemplo.catapi.controller;

import com.exemplo.catapi.model.Raca;
import com.exemplo.catapi.service.RacaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/racas")
public class RacaController {

    private final RacaService racaService;

    public RacaController(RacaService racaService) {
        this.racaService = racaService;
    }

    @GetMapping
    public List<Raca> listarTodas() {
        return racaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Raca buscarPorId(@PathVariable String id) {
        return racaService.buscarPorId(id);
    }

    @GetMapping("/temperamento/{temp}")
    public List<Raca> buscarPorTemperamento(@PathVariable String temp) {
        return racaService.buscarPorTemperamento(temp);
    }

    @GetMapping("/origem/{origem}")
    public List<Raca> buscarPorOrigem(@PathVariable String origem) {
        return racaService.buscarPorOrigem(origem);
    }
}
