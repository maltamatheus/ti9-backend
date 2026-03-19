package com.ti9.ti9_backend.mapping.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.embbedables.Endereco;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoUpdateDto {
    private UUID id;
    private Fornecedor fornecedor;
    @Enumerated(EnumType.STRING)
    private EnumTipoDocumento tipoDocumento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEmissao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataValidade;
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
    private String urlArquivo;
    private String observacoes;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataUpload;
}
