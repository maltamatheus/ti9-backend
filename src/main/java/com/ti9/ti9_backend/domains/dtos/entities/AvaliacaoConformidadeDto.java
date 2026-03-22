package com.ti9.ti9_backend.domains.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.embbedables.Criterio;
import com.ti9.ti9_backend.domains.enums.EnumResultado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AvaliacaoConformidadeDto {

    private UUID idFornecedor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAvaliacao;

    private String avaliador;

    private List<Criterio> criterios;

    private Long pontuacaoTotal;

    @Enumerated(EnumType.STRING)
    private EnumResultado resultado;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate proximaAvaliacao;

    private String comentarios;
}