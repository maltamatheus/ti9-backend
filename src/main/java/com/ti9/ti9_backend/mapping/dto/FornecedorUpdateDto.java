package com.ti9.ti9_backend.mapping.dto;

import com.ti9.ti9_backend.domains.embbedables.Endereco;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.domains.enums.EnumPorte;
import com.ti9.ti9_backend.domains.enums.EnumTipoFornecedor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorUpdateDto {
    private UUID id;
    private String codigo;
    private String razaoSocial;
    private String cnpj;
    private EnumTipoFornecedor tipo;
    private String segmento;
    private EnumPorte porte;
    private Endereco endereco;
    private String telefone; // Validar formato
    private String email; // Validar formato
    private String site; // validar URL
    private Boolean ativo = true;
    private LocalDateTime dataCadastro = LocalDateTime.now();
    private LocalDateTime dataUltimaAtualizacao;
    private EnumCategoriaRisco categoriaRisco;
    private Integer pontuacaoConformidade;
    private String observacoes;

}
