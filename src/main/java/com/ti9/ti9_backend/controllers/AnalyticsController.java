package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.services.AnalyticsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsServices analyticsServices;
    @ResponseBody
    @GetMapping("/resumo")
    public ResponseEntity<?> obterResumo(){
        return ResponseEntity.ok(analyticsServices.obterResumoAtivosInativos());
    }
    @ResponseBody
    @GetMapping("/conformidade")
    public ResponseEntity<?> obterConformidade(){
        return ResponseEntity.ok("Conformidade");
    }
    @ResponseBody
    @GetMapping("/documentos/status")
    public ResponseEntity<?> obterStatusDocumentos(){
        return ResponseEntity.ok("Status dos Documentos");
    }
}
