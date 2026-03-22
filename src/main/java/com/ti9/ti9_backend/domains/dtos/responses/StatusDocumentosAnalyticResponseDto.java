package com.ti9.ti9_backend.domains.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDocumentosAnalyticResponseDto {
    private Map<String,Long> statusDocumentos;
}
