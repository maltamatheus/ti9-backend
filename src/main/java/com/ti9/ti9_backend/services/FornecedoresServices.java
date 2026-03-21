package com.ti9.ti9_backend.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.ti9.ti9_backend.domains.dtos.responses.AvaliacaoConformidadeResponseDto;
import com.ti9.ti9_backend.domains.dtos.responses.DocumentoResponseDto;
import com.ti9.ti9_backend.domains.dtos.responses.FornecedorResponseDto;
import com.ti9.ti9_backend.domains.embbedables.Endereco;
import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.domains.enums.EnumPorte;
import com.ti9.ti9_backend.domains.enums.EnumTipoFornecedor;
import com.ti9.ti9_backend.domains.enums.EnumUF;
import com.ti9.ti9_backend.exceptions.OperacaoNaoRealizadaException;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import com.ti9.ti9_backend.mapping.FornecedorMapper;
import com.ti9.ti9_backend.mapping.dto.FornecedorUpdateDto;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
                .documentos((documentos == null || documentos.isEmpty()) ? null : documentos)
                .avaliacoes((avaliacoes == null || avaliacoes.isEmpty()) ? null : avaliacoes)
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
        try{
            Fornecedor baseFornecedor = getFornecedor(fornecedorUpdateDto.getId());
            fornecedorMapper.updateFornecedorFromDto(fornecedorUpdateDto,baseFornecedor);
            Fornecedor fornecedorAtualizado = salvaFornecedor(baseFornecedor);
            List<Documento> documentos = documentosServices.obterDocumentosFornecedor(id);
            List<AvaliacaoConformidade> avaliacoes = avaliacaoConformidadeServices.obterAvaliacoesFornecedor(id);
            return FornecedorResponseDto.builder()
                    .fornecedor(fornecedorAtualizado)
                    .documentos((documentos == null || documentos.isEmpty()) ? null : documentos)
                    .avaliacoes((avaliacoes == null || avaliacoes.isEmpty()) ? null : avaliacoes)
                    .build();
        } catch (Exception e) {
            throw new OperacaoNaoRealizadaException("Operação não realizada.\n"+ e.getMessage());
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
        Documento novoDocumento = null;
        if(documento.getId() == null){
            documento.setFornecedor(obterFornecedor(id).getFornecedor());
            novoDocumento = documentosServices.criarDocumento(documento);
        } else if (documento.getFornecedor() == null ){
            throw new OperacaoNaoRealizadaException("Fornecedor do Documento é obrigatório");
        }
        return DocumentoResponseDto.builder()
                    .documento(novoDocumento)
                    .build();
    }
    public AvaliacaoConformidadeResponseDto adicionarAvaliacao(UUID id, AvaliacaoConformidade avaliacaoConformidade) {
        AvaliacaoConformidade novaAvaliacao = null;
        if(avaliacaoConformidade.getId() == null){
            avaliacaoConformidade.setFornecedor(obterFornecedor(id).getFornecedor());
            novaAvaliacao = avaliacaoConformidadeServices.criarAvaliacaoConformidade(avaliacaoConformidade);
        } else if (avaliacaoConformidade.getFornecedor() == null ){
            throw new OperacaoNaoRealizadaException("Fornecedor da Avaliação é obrigatório");
        }
        return AvaliacaoConformidadeResponseDto.builder()
                .avaliacaoConformidade(novaAvaliacao)
                .build();
    }
    public Page<Documento> obterDocumentos(UUID id, Pageable pageable) {
        return documentosServices.obterDocumentosFornecedor(id,pageable);
    }
    public List<Fornecedor> uploadFornecedoresFile(MultipartFile file) {
        List<Fornecedor> fornecedores = new ArrayList<>();
        try{
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                 CSVReader csvReader = new CSVReader(reader)) {

                String[] line;
                csvReader.readNext(); // Pular o cabeçalho, se houver
                while ((line = csvReader.readNext()) != null) {
                    String registro = line[0];
                    Fornecedor f = new Fornecedor();
                    f.setCodigo(registro.split(";")[0]);
                    f.setRazaoSocial(registro.split(";")[1]);
                    f.setCnpj(registro.split(";")[3]);
                    f.setTipo(EnumTipoFornecedor.valueOf(registro.split(";")[4]));
                    f.setSegmento(registro.split(";")[5]);
                    f.setPorte(EnumPorte.valueOf(registro.split(";")[6]));

                    Endereco end = new Endereco();
                    end.setLogradouro(registro.split(";")[7]);
                    end.setNumero(registro.split(";")[8]);
                    end.setBairro(registro.split(";")[9]);
                    end.setCidade(registro.split(";")[10]);
                    end.setEstado(EnumUF.valueOf(registro.split(";")[11]));
                    end.setCep(registro.split(";")[12]);
                    f.setEndereco(end);

                    f.setTelefone(registro.split(";")[13]);
                    f.setEmail(registro.split(";")[14]);
                    f.setCategoriaRisco(EnumCategoriaRisco.valueOf(registro.split(";")[15]));

                    fornecedores.add(f);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (CsvValidationException ex) {
                throw new RuntimeException(ex);
            }
            return fornecedorRepository.saveAll(fornecedores);
        } catch (Exception e){
            throw new OperacaoNaoRealizadaException("Falha no import do arquivo de fornecedores\n" + e.getMessage());
        }
    }
    private Fornecedor getFornecedor(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
    }
    private Fornecedor salvaFornecedor(Fornecedor fornecedor){
        try {
            fornecedor.setDataUltimaAtualizacao(LocalDateTime.now());
            return fornecedorRepository.save(fornecedor);
        } catch (Exception e) {
            throw new ValorNaoPermitidoException("Valor Inválido ou não permitido\n" + e.getMessage());
        }
    }

}
