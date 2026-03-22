package com.ti9.ti9_backend.domains.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumoAnalyticResponseDto {
    private Map<Boolean,Long> groupAtivosInativos;
    private Map<EnumCategoriaRisco,Long> groupCategoriasRisco;
    private Map<String,Long> groupSegmentos;
    private Long documentosVencidos;
}
