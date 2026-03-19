package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.mapping.dto.DocumentoUpdateDto;
import com.ti9.ti9_backend.services.DocumentosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documentos")
public class DocumentosController {
    @Autowired
    private DocumentosServices documentosServices;
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, DocumentoUpdateDto documentoUpdateDto){
        return ResponseEntity.ok(documentosServices.atualizarDocumento(id,documentoUpdateDto));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable UUID id){
        documentosServices.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @ResponseBody
    @GetMapping("/vencidos")
    public ResponseEntity<List<Documento>> obterVencidos(){
        return ResponseEntity.ok(documentosServices.obterVencidos());
    }
}
