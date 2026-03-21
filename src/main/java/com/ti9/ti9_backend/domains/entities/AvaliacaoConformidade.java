package com.ti9.ti9_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.embbedables.Criterio;
import com.ti9.ti9_backend.domains.enums.EnumResultado;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="tab_avaliacao_conformidade")
public class AvaliacaoConformidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_fornecedor",nullable = false)
    private Fornecedor fornecedor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAvaliacao;

    private String avaliador;

    @ElementCollection
    @CollectionTable(name="tab_criterios",joinColumns = @JoinColumn(name="nome_criterio"))
    private List<Criterio> criterios;

    private Long pontuacaoTotal;

    @Enumerated(EnumType.STRING)
    private EnumResultado resultado;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate proximaAvaliacao;

    @Column(columnDefinition = "text")
    private String comentarios;
}