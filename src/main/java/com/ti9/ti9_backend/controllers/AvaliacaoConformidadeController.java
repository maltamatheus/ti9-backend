package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.services.AvaliacaoConformidadeServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoConformidadeController {

    @Autowired
    private AvaliacaoConformidadeServices avaliacaoConformidadeServices;
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoConformidade> obter(@PathVariable UUID id){
        return ResponseEntity.ok(avaliacaoConformidadeServices.obter(id));
    }
}
