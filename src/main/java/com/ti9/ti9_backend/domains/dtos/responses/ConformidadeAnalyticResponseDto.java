package com.ti9.ti9_backend.domains.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConformidadeAnalyticResponseDto {
    private Map<String,Map<String,Double>> mediaPontuacaoSegmento;
    private Map<String,Map<LocalDateTime,Long>> evolucaoTemporalFornecedor;
    private Map<String,Long> melhores;
    private Map<String,Long> piores;
}
