package com.exemplo.catapi.service;

import com.exemplo.catapi.model.Imagem;
import com.exemplo.catapi.model.Raca;
import com.exemplo.catapi.repository.ImagemRepository;
import com.exemplo.catapi.repository.RacaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class ColetaService {

    private final RacaRepository racaRepository;
    private final ImagemRepository imagemRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ColetaService(RacaRepository racaRepository, ImagemRepository imagemRepository) {
        this.racaRepository = racaRepository;
        this.imagemRepository = imagemRepository;
    }

    @PostConstruct
    public void coletarDados() {
        String resposta = chamarAPIComRetentativa("https://api.thecatapi.com/v1/breeds", 3, 10);
        if (resposta == null) return; // aborta se não conseguiu resposta

        try {
            JsonNode racasJson = objectMapper.readTree(resposta);
            for (JsonNode racaJson : racasJson) {
                String nome = racaJson.path("name").asText();
                String origem = racaJson.path("origin").asText();
                String temperamento = racaJson.path("temperament").asText();
                String descricao = racaJson.path("description").asText();

                Raca raca = new Raca();
                raca.setNome(nome);
                raca.setOrigem(origem);
                raca.setTemperamento(temperamento);
                raca.setDescricao(descricao.length() > 1000 ? descricao.substring(0, 1000) : descricao);
                racaRepository.save(raca);

                String racaId = racaJson.path("id").asText();
                String imagensJson = chamarAPIComRetentativa("https://api.thecatapi.com/v1/images/search?limit=3&breed_id=" + racaId, 3, 10);
                if (imagensJson == null) continue;

                JsonNode imagensArray = objectMapper.readTree(imagensJson);
                for (JsonNode imagemJson : imagensArray) {
                    String url = imagemJson.path("url").asText();
                    Imagem imagem = new Imagem();
                    imagem.setRaca(raca);
                    imagem.setUrl(url);
                    imagem.setTipo("RAÇA");
                    imagemRepository.save(imagem);
                }
            }

            // Salvar imagens de gatos com chapéu
            salvarImagensPorCategoria("hat", "CHAPÉU");

            // Salvar imagens de gatos com óculos
            salvarImagensPorCategoria("glasses", "ÓCULOS");

        } catch (Exception e) {
            System.out.println("Erro ao processar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void salvarImagensPorCategoria(String categoria, String tipo) {
        String url = "https://api.thecatapi.com/v1/images/search?limit=3&category_ids=" + buscarIdCategoria(categoria);
        String resposta = chamarAPIComRetentativa(url, 3, 10);
        if (resposta == null) return;

        try {
            JsonNode imagensArray = objectMapper.readTree(resposta);
            for (JsonNode imagemJson : imagensArray) {
                String urlImagem = imagemJson.path("url").asText();
                Imagem imagem = new Imagem();
                imagem.setUrl(urlImagem);
                imagem.setTipo(tipo);
                imagemRepository.save(imagem);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar imagens de categoria " + tipo + ": " + e.getMessage());
        }
    }

    // Troca switch por if-else
    private String buscarIdCategoria(String categoria) {
        if ("hat".equalsIgnoreCase(categoria)) {
            return "1";
        } else if ("glasses".equalsIgnoreCase(categoria)) {
            return "4";
        } else {
            return "0";
        }
    }

    private String chamarAPIComRetentativa(String url, int tentativas, int esperaSegundos) {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 1; i <= tentativas; i++) {
            try {
                return restTemplate.getForObject(url, String.class);
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("Limite de requisições atingido (tentativa " + i + "/" + tentativas + "). Aguardando " + esperaSegundos + "s...");
                try {
                    Thread.sleep(esperaSegundos * 1000L);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Erro ao chamar API: " + e.getMessage());
                return null;
            }
        }

        System.out.println("Tentativas esgotadas. Não foi possível coletar os dados da API.");
        return null;
    }
}