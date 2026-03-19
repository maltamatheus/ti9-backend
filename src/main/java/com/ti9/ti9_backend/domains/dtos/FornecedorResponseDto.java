package com.ti9.ti9_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class FornecedorResponseDto {
    private Fornecedor fornecedor;
    private List<Documento> documentos;
    private List<AvaliacaoConformidade> avaliacoes;
    private String msg;
}
