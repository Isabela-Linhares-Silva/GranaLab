package com.example.granalab.controller;

import com.example.granalab.service.AvatarCatalog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoints das opções oficiais de avatar. */
@RestController
@RequestMapping("/avatares")
public class AvatarController {

    private final AvatarCatalog avatarCatalog;

    public AvatarController(AvatarCatalog avatarCatalog) {
        this.avatarCatalog = avatarCatalog;
    }

    @GetMapping
    public List<String> listar() {
        return avatarCatalog.listar();
    }
}
