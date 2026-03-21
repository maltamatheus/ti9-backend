package com.ti9.ti9_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.embbedables.Endereco;
import com.ti9.ti9_backend.domains.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="tab_documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_fornecedor",nullable = false)
    private Fornecedor fornecedor;

    @Enumerated(EnumType.STRING)
    private EnumTipoDocumento tipoDocumento;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEmissao;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    private String urlArquivo;

    @Column(columnDefinition = "text")
    private String observacoes;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataUpload;
    }
