package com.exemplo.catapi.service;

import com.exemplo.catapi.model.Imagem;
import com.exemplo.catapi.model.Raca;
import com.exemplo.catapi.repository.ImagemRepository;
import com.exemplo.catapi.repository.RacaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ColetaService {

    private final RacaRepository racaRepository;
    private final ImagemRepository imagemRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String BASE_URL = "https://api.thecatapi.com/v1";

    @PostConstruct
    public void coletarDados() {
        // a. Coletar raças
        RacaResposta[] racas = restTemplate.getForObject(BASE_URL + "/breeds", RacaResposta[].class);

        if (racas != null) {
            for (RacaResposta racaApi : racas) {
                Raca novaRaca = new Raca();
                novaRaca.setNome(racaApi.getName());
                novaRaca.setOrigem(racaApi.getOrigin());
                novaRaca.setTemperamento(racaApi.getTemperament());
                novaRaca.setDescricao(racaApi.getDescription());

                Raca salva = racaRepository.save(novaRaca);

                // b. Coletar 3 imagens para cada raça
                String urlImagens = BASE_URL + "/images/search?limit=3&breed_id=" + racaApi.getId();
                ImagemResposta[] imagens = restTemplate.getForObject(urlImagens, ImagemResposta[].class);
                if (imagens != null) {
                    for (ImagemResposta img : imagens) {
                        Imagem imagem = new Imagem();
                        imagem.setUrl(img.getUrl());
                        imagem.setTipo("raca");
                        imagem.setRaca(salva);
                        imagemRepository.save(imagem);
                    }
                }
            }
        }

        // c. 3 imagens de gatos com chapéu
        salvarImagensPorCategoria("1", "chapéu");

        // d. 3 imagens de gatos com óculos
        salvarImagensPorCategoria("4", "óculos");
    }

    private void salvarImagensPorCategoria(String categoriaId, String tipo) {
        String url = BASE_URL + "/images/search?limit=3&category_ids=" + categoriaId;
        ImagemResposta[] imagens = restTemplate.getForObject(url, ImagemResposta[].class);
        if (imagens != null) {
            for (ImagemResposta img : imagens) {
                Imagem imagem = new Imagem();
                imagem.setUrl(img.getUrl());
                imagem.setTipo(tipo);
                imagemRepository.save(imagem);
            }
        }
    }

    // Classe auxiliar interna para mapear resposta da API de imagem
    private static class ImagemResposta {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    // Classe auxiliar interna para mapear resposta da API de raça
    private static class RacaResposta {
        private String id;
        private String name;
        private String origin;
        private String temperament;
        private String description;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getOrigin() { return origin; }
        public void setOrigin(String origin) { this.origin = origin; }

        public String getTemperament() { return temperament; }
        public void setTemperament(String temperament) { this.temperament = temperament; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
