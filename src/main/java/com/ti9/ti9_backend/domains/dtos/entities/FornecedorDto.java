package com.ti9.ti9_backend.domains.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti9.ti9_backend.domains.embbedables.Endereco;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.domains.enums.EnumPorte;
import com.ti9.ti9_backend.domains.enums.EnumTipoFornecedor;
import com.ti9.ti9_backend.utils.StringUtils;
import com.ti9.ti9_backend.utils.annotations.TelefoneValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Data
public class FornecedorDto {
    @Size(max = 10,message = "Tamanho não pode ser superior à 10 caracteres")
    private String codigo;

    @NotBlank(message = "Razão Social é obrigatório")
    @Size(min = 3,message = "Tamanho não pode ser inferior à 10 caracteres")
    private String razaoSocial;

    @CNPJ(message = "CNPJ inválido")
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @Enumerated(EnumType.STRING)
    private EnumTipoFornecedor tipo;

    @NotBlank(message = "Segmento é obrigatório")
    private String segmento;

    @Enumerated(EnumType.STRING)
    private EnumPorte porte;

    @Embedded
    private Endereco endereco;

    @TelefoneValido(message = "Celular inválido")
    @Schema(example = "Formatos Válidos(Celular): (11) 91234-5678, 11912345678, 11 912345678.")
    private String telefone;

    @Email(message = "Favor informar E-mail válido")
    private String email;

    @URL(message = "Endereço de site inválido")
    private String site;

    private Boolean ativo = true;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataUltimaAtualizacao;

    @Enumerated(EnumType.STRING)
    private EnumCategoriaRisco categoriaRisco;

    private Integer pontuacaoConformidade;

    private String observacoes;

    public void setCnpj(String cnpj) {
        this.cnpj = StringUtils.filtraDigitosNumericos(cnpj);
    }

    public void setTelefone(String telefone) {
        this.telefone = StringUtils.filtraDigitosNumericos(telefone);
    }
}
