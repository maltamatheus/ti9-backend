package com.ti9.ti9_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConformidadeAnalyticDto {
    private Map<String,Map<String,Double>> mediaPontuacaoSegmento;
    private Map<String,Map<LocalDate,Long>> evolucaoTemporalFornecedor;
    private Map<String,Long> melhores;
    private Map<String,Long> piores;
}
