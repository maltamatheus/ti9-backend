package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.dtos.entities.DocumentoDto;
import com.ti9.ti9_backend.domains.dtos.responses.DocumentoResponseDto;
import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.exceptions.OperacaoNaoRealizadaException;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import com.ti9.ti9_backend.mapping.DocumentoMapper;
import com.ti9.ti9_backend.mapping.dto.DocumentoUpdateDto;
import com.ti9.ti9_backend.repositories.DocumentoRepository;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentosServices {
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private DocumentoMapper documentoMapper;

    public Documento criarDocumento(DocumentoDto documentoDto){
        return salvarDocumento(dtoToEntityToInsert(documentoDto));
    }
    public List<Documento> obterDocumentosFornecedor(UUID id){
        return documentoRepository.obterDocumentosFornecedor(id);
    }
    public Page<Documento> obterDocumentosFornecedor(UUID id, Pageable pageable){
        return documentoRepository.obterDocumentosFornecedor(id,pageable);
    }
    public DocumentoResponseDto atualizarDocumento(UUID id, DocumentoUpdateDto documentoUpdateDto) {
        try {
            Documento baseDocumento = obterDocumento(documentoUpdateDto.getId());
            documentoMapper.updateDocumentoFromDto(documentoUpdateDto, baseDocumento);
            Documento documentoAtualizado = salvarDocumento(baseDocumento);
            return DocumentoResponseDto.builder()
                    .documento(documentoAtualizado)
                    .build();
        } catch (Exception e) {
            throw new OperacaoNaoRealizadaException("Operação não relizada.\n" + e.getMessage());
        }
    }

    public void excluir(UUID id) {
        documentoRepository.delete(obterDocumento(id));
    }
    public List<Documento> obterVencidos() {
        return documentoRepository.obterVencidos(LocalDate.now());
    }

    private Documento obterDocumento(UUID id){
        return documentoRepository
                .findById(id).orElseThrow(() ->
                        new RecursoNaoEncontradoException("Documento não encontrado"));
    }
    private Documento salvarDocumento(Documento documento){
        try {
            return documentoRepository.save(documento);
        } catch (Exception e) {
            throw new ValorNaoPermitidoException("Valor Inválido ou não permitido\n" + e.getMessage());
        }
    }

    private Documento dtoToEntityToInsert(DocumentoDto dto){
        Documento novoDocumento = new Documento();
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(dto.getIdFornecedor());
        if(fornecedor.isPresent()){
            novoDocumento.setFornecedor(fornecedor.get());
        } else {
            throw new OperacaoNaoRealizadaException("Falha ao indicar fornecedor do documento");
        }

        novoDocumento.setTipoDocumento(dto.getTipoDocumento());
        novoDocumento.setDataEmissao(dto.getDataEmissao());
        novoDocumento.setDataValidade(dto.getDataValidade());
        novoDocumento.setStatus(dto.getStatus());
        novoDocumento.setUrlArquivo(dto.getUrlArquivo());
        novoDocumento.setObservacoes(dto.getObservacoes());
        novoDocumento.setDataUpload(LocalDateTime.now());

        return novoDocumento;
    }

}
