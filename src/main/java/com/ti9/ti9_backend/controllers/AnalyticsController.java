package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.domains.dtos.responses.ConformidadeAnalyticResponseDto;
import com.ti9.ti9_backend.domains.dtos.responses.ResumoAnalyticResponseDto;
import com.ti9.ti9_backend.services.AnalyticsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsServices analyticsServices;
    @ResponseBody
    @GetMapping("/resumo")
    public ResponseEntity<ResumoAnalyticResponseDto> obterResumo(){
        return ResponseEntity.ok(analyticsServices.obterResumos());
    }
    @ResponseBody
    @GetMapping("/conformidade")
    public ResponseEntity<ConformidadeAnalyticResponseDto> obterConformidade(){
        return ResponseEntity.ok(analyticsServices.obterConformidades());
    }
    @ResponseBody
    @GetMapping("/documentos/status")
    public ResponseEntity<?> obterStatusDocumentos(){
        return ResponseEntity.ok(analyticsServices.obterStatusDocumentos());
    }
}
