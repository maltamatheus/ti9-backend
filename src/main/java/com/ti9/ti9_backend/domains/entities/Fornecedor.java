package com.ti9.ti9_backend.domains.entities;

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
@Table(name="tab_fornecedores")
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

    private String telefone;

    private String email;

    private String site;

    private Boolean ativo;

    private LocalDateTime dataCadastro;

    private LocalDateTime dataUltimaAtualizacao;

    @Enumerated(EnumType.STRING)
    private EnumCategoriaRisco categoriaRisco;

    @Column(name="PONTUACAO_CONFORMIDADE",columnDefinition = "INTEGER CHECK (PONTUACAO_CONFORMIDADE BETWEEN 0 AND 100)")
    private Integer pontuacaoConformidade;

    @Column(columnDefinition = "text")
    private String observacoes;
}
