package com.ti9.ti9_backend.domains.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ti9.ti9_backend.domains.entities.Documento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentoResponseDto {
    private Documento documento;
    private String msg;
}
