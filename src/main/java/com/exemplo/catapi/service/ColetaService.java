package com.exemplo.catapi.service;

import com.exemplo.catapi.model.Imagem;
import com.exemplo.catapi.model.Raca;
import com.exemplo.catapi.repository.ImagemRepository;
import com.exemplo.catapi.repository.RacaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class ColetaService {

    private final RacaRepository racaRepository;
    private final ImagemRepository imagemRepository;

    public ColetaService(RacaRepository racaRepository, ImagemRepository imagemRepository) {
        this.racaRepository = racaRepository;
        this.imagemRepository = imagemRepository;
    }

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.thecatapi.com/v1/breeds";

        Raca[] racas = restTemplate.getForObject(url, Raca[].class);
        if (racas != null) {
            for (Raca r : racas) {
                if (r.getId() != null) {
                    racaRepository.save(r);

                    for (int i = 0; i < 3; i++) {
                        String imageUrl = "https://api.thecatapi.com/v1/images/search?breed_id=" + r.getId();
                        Map[] images = restTemplate.getForObject(imageUrl, Map[].class);
                        if (images != null && images.length > 0 && images[0].get("url") != null) {
                            Imagem img = new Imagem();
                            img.setUrl(images[0].get("url").toString());
                            img.setTipo("geral");
                            img.setRaca(r);
                            imagemRepository.save(img);
                        }
                    }
                }
            }
        }

        coletarImagensTematicas("hat", "chapeu", restTemplate);
        coletarImagensTematicas("sunglasses", "oculos", restTemplate);
    }

    private void coletarImagensTematicas(String categoria, String tipo, RestTemplate restTemplate) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.thecatapi.com/v1/images/search")
                .queryParam("category_ids", categoria)
                .queryParam("limit", 3)
                .toUriString();

        Map[] imagens = restTemplate.getForObject(url, Map[].class);
        if (imagens != null) {
            for (Map imgMap : imagens) {
                if (imgMap.get("url") != null) {
                    Imagem img = new Imagem();
                    img.setUrl(imgMap.get("url").toString());
                    img.setTipo(tipo);
                    imagemRepository.save(img);
                }
            }
        }
    }
}
