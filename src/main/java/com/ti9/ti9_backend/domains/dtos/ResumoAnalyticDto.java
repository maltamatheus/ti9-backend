package com.ti9.ti9_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumoAnalyticDto {
    private Map<Boolean,Long> groupAtivosInativos;
    private Map<String,Long> groupCategoriasRisco;
    private Map<String,Long> groupSegmentos;
    private Long documentosVencidos;
}
