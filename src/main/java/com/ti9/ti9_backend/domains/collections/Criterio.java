package com.ti9.ti9_backend.domains.collections;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Criterio {

    @Column(name="nome_criterio", updatable = false,insertable = false)
    private String nomeCriterio;

    @Column(columnDefinition = "INTEGER CHECK (PONTUACAO BETWEEN 0 AND 10)")
    private Integer pontuacao;

    private String observacao;
}
