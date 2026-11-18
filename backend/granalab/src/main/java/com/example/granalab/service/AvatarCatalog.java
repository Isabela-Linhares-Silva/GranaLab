package com.example.granalab.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Catálogo de caminhos de avatar aprovados pela equipe. */
@Component
public class AvatarCatalog {

    private static final List<String> AVATARES = List.of(
            "/resources/img/Onca.jpeg",
            "/resources/img/Arara.jpeg",
            "/resources/img/Lobo-guara.jpeg",
            "/resources/img/Garca.jpeg",
            "/resources/img/Porco.jpeg",
            "/resources/img/Tartaruga.jpeg");

    public List<String> listar() {
        return AVATARES;
    }

    public String selecionarAleatorio() {
        return AVATARES.get(ThreadLocalRandom.current().nextInt(AVATARES.size()));
    }

    public boolean contem(String caminho) {
        return AVATARES.contains(caminho);
    }
}
