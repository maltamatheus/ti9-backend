package com.ti9.ti9_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.embbedables.Endereco;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.domains.enums.EnumPorte;
import com.ti9.ti9_backend.domains.enums.EnumTipoFornecedor;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="tab_fornecedor")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true,length = 10)
    private String codigo;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false,unique = true,length = 14)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    private EnumTipoFornecedor tipo;

    @Column(nullable = false)
    private String segmento;

    @Enumerated(EnumType.STRING)
    private EnumPorte porte;

    @Embedded
    private Endereco endereco;

    private String telefone; // Validar formato
    private String email; // Validar formato
    private String site; // validar URL
    private Boolean ativo = true;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataUltimaAtualizacao;

    @Enumerated(EnumType.STRING)
    private EnumCategoriaRisco categoriaRisco;

    @Column(name="PONTUACAO_CONFORMIDADE",columnDefinition = "INTEGER CHECK (PONTUACAO_CONFORMIDADE BETWEEN 0 AND 100)")
    private Integer pontuacaoConformidade;

    @Column(columnDefinition = "text")
    private String observacoes;

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj.replaceAll("[^a-zA-Z0-9]", "");
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone.replaceAll("[^a-zA-Z0-9]", "");
    }
}
