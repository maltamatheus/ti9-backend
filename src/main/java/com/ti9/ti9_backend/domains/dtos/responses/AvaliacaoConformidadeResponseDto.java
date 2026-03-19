package com.ti9.ti9_backend.domains.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoConformidadeResponseDto {
    private AvaliacaoConformidade avaliacaoConformidade;
    private String msg;
}
