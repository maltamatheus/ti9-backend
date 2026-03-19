package com.ti9.ti9_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConformidadeAnalyticDto {
    private Map<String,Map<String,Double>> mediaPontuacaoSegmento;
    private Map<String,Long> evolucaoTemporalFornecedor;
    private Map<String,Long> melhores;
    private Map<String,Long> piores;
}
