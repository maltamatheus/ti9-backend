package com.ti9.ti9_backend.domains.embbedables;

import com.ti9.ti9_backend.domains.enums.EnumUF;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Endereco {
    private String logradouro;
    private String numero; // Informações numéricas não utilizada para cálculos - uso String
    private String complemento;
    private String bairro;
    private String cidade;
    @Enumerated(EnumType.STRING)
    private EnumUF estado;
    private String cep;
}
