package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.dtos.DocumentoResponseDto;
import com.ti9.ti9_backend.domains.dtos.FornecedorResponseDto;
import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import com.ti9.ti9_backend.mapping.FornecedorMapper;
import com.ti9.ti9_backend.mapping.dto.FornecedorUpdateDto;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import com.ti9.ti9_backend.utils.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedoresServices {
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private DocumentosServices documentosServices;
    @Autowired
    private AvaliacaoConformidadeServices avaliacaoConformidadeServices;
    @Autowired
    private FornecedorMapper fornecedorMapper;
    public Fornecedor criarFornecedor(Fornecedor fornecedor){
        return salvaFornecedor(fornecedor);
    }

    public FornecedorResponseDto obterFornecedor(UUID idFornecedor){
        Fornecedor fornecedor = getFornecedor(idFornecedor);
        List<Documento> documentos = documentosServices.obterDocumentosFornecedor(idFornecedor);
        List<AvaliacaoConformidade> avaliacoes = avaliacaoConformidadeServices.obterAvaliacoesFornecedor(idFornecedor);
        return FornecedorResponseDto.builder()
                .fornecedor(fornecedor)
                .documentos(documentos)
                .avaliacoes(avaliacoes)
                .build();
    }

    public Page<Fornecedor> obterFornecedores(String nome,
                                              String cnpj,
                                              String segmento,
                                              EnumCategoriaRisco categoriaRisco,
                                              Boolean ativo,
                                              Pageable pageable) {

        return fornecedorRepository.obterFornecedores(nome,
                                                    cnpj,
                                                    segmento,
                                                    categoriaRisco,
                                                    ativo,
                                                    pageable);
    }

    public FornecedorResponseDto atualizarFornecedor(UUID id, FornecedorUpdateDto fornecedorUpdateDto){
        if(Validacoes.idCorreto(id,fornecedorUpdateDto.getId())){
            Fornecedor baseFornecedor = getFornecedor(fornecedorUpdateDto.getId());
            fornecedorMapper.updateFornecedorFromDto(fornecedorUpdateDto,baseFornecedor);
            Fornecedor fornecedorAtualizado = salvaFornecedor(baseFornecedor);
            return FornecedorResponseDto.builder()
                    .fornecedor(fornecedorAtualizado)
                    .build();
        } else {
            return FornecedorResponseDto.builder()
                    .msg("Operação não executada: Id desejado é diferente do Id informado")
                    .build();
        }
    }


    public Fornecedor ativarDesativarFornecedor(UUID id) {
        Fornecedor baseFornecedor = getFornecedor(id);
        baseFornecedor.setAtivo(!baseFornecedor.getAtivo());
        return salvaFornecedor(baseFornecedor);
    }

    public Fornecedor excluir(UUID id) { // Exclusão Lógica
        Fornecedor baseFornecedor = getFornecedor(id);
        baseFornecedor.setAtivo(false);
        return salvaFornecedor(baseFornecedor);
    }
    public DocumentoResponseDto adicionarDocumento(UUID id, Documento documento) {
        if(Validacoes.idCorreto(id,documento.getFornecedor().getId())) {
            return DocumentoResponseDto.builder()
                    .documento(documentosServices.criarDocumento(documento))
                    .build();
        } else {
            return DocumentoResponseDto.builder()
                    .msg("Operação não executada: Id desejado é diferente do Id informado")
                    .build();
        }
    }
    public Page<Documento> obterDocumentos(UUID id, Pageable pageable) {
        return documentosServices.obterDocumentosFornecedor(id,pageable);
    }
    private Fornecedor getFornecedor(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
    }
    private Fornecedor salvaFornecedor(Fornecedor fornecedor){
        try {
            return fornecedorRepository.save(fornecedor);
        } catch (Exception e) {
            throw new ValorNaoPermitidoException(e.getMessage());
        }
    }
}
