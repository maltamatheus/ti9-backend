package com.ti9.ti9_backend.domains.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumStatus;
import com.ti9.ti9_backend.domains.enums.EnumTipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DocumentoDto {

    private UUID idFornecedor;

    @Enumerated(EnumType.STRING)
    private EnumTipoDocumento tipoDocumento;

    @NotNull(message = "Data de emissão é obrigatória")
    @Schema(example = "Formato: Ano com 4 dígitos;Mês com 2 dígitos;Dia com 2 dígitos (AAAA-MM-DD)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEmissao;

    @NotNull(message = "Data de emissão é obrigatória")
    @Schema(example = "Formato: Ano com 4 dígitos;Mês com 2 dígitos;Dia com 2 dígitos (AAAA-MM-DD)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    private String urlArquivo;

    private String observacoes;
    }
