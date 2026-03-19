package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.domains.dtos.ConformidadeAnalyticDto;
import com.ti9.ti9_backend.domains.dtos.ResumoAnalyticDto;
import com.ti9.ti9_backend.services.AnalyticsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsServices analyticsServices;
    @ResponseBody
    @GetMapping("/resumo")
    public ResponseEntity<ResumoAnalyticDto> obterResumo(){
        return ResponseEntity.ok(analyticsServices.obterResumos());
    }
    @ResponseBody
    @GetMapping("/conformidade")
    public ResponseEntity<ConformidadeAnalyticDto> obterConformidade(){
        return ResponseEntity.ok(analyticsServices.obterConformidades());
    }
    @ResponseBody
    @GetMapping("/documentos/status")
    public ResponseEntity<?> obterStatusDocumentos(){
        return ResponseEntity.ok("Status dos Documentos");
    }
}
