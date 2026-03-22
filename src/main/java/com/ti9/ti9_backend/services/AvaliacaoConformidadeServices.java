package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.dtos.entities.AvaliacaoConformidadeDto;
import com.ti9.ti9_backend.domains.embbedables.Criterio;
import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.exceptions.OperacaoNaoRealizadaException;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.repositories.AvaliacaoConformidadeRepository;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AvaliacaoConformidadeServices {
    private AvaliacaoConformidadeRepository avaliacaoConformidadeRepository;
    private FornecedorRepository fornecedorRepository;

    public AvaliacaoConformidade criarAvaliacaoConformidade(AvaliacaoConformidadeDto avaliacaoDto){
        return salvar(dtoToEntityToInsert(avaliacaoDto));
    }
    public List<AvaliacaoConformidade> obterAvaliacoesFornecedor(UUID idFornecedor){
        return avaliacaoConformidadeRepository.obterAvaliacoesFornecedor(idFornecedor);
    }

    public AvaliacaoConformidade obter(UUID id) {
        return getAvaliacaoConformidade(id);
    }

    private AvaliacaoConformidade salvar(AvaliacaoConformidade avaliacaoConformidade){
        return avaliacaoConformidadeRepository.save(avaliacaoConformidade);
    }

    private AvaliacaoConformidade getAvaliacaoConformidade(UUID id){
        return avaliacaoConformidadeRepository.findById(id)
                .orElseThrow(()->new RecursoNaoEncontradoException("Avaliação não encontrada"));
    }

    private AvaliacaoConformidade dtoToEntityToInsert(AvaliacaoConformidadeDto dto){
        AvaliacaoConformidade novaAvaliacao = new AvaliacaoConformidade();
        Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(dto.getIdFornecedor());
        if(fornecedorOpt.isPresent()){
            novaAvaliacao.setFornecedor(fornecedorOpt.get());
        } else {
            throw new OperacaoNaoRealizadaException("Falha ao indicar fornecedor na Avaliação");
        }
        novaAvaliacao.setDataAvaliacao(LocalDateTime.now());
        novaAvaliacao.setAvaliador(dto.getAvaliador());
        novaAvaliacao.setCriterios(new LinkedHashSet<>(dto.getCriterios()));
        Long pontuacaoTotal = 0l;
        for(Criterio criterio : dto.getCriterios()){
            pontuacaoTotal += criterio.getPontuacao();
        }
        novaAvaliacao.setPontuacaoTotal(pontuacaoTotal);
        novaAvaliacao.setResultado(dto.getResultado());
        novaAvaliacao.setProximaAvaliacao(dto.getProximaAvaliacao());
        novaAvaliacao.setComentarios(dto.getComentarios());

        return novaAvaliacao;
    }
}
