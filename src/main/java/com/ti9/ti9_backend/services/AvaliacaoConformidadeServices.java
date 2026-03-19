package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.repositories.AvaliacaoConformidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AvaliacaoConformidadeServices {
    @Autowired
    private AvaliacaoConformidadeRepository avaliacaoConformidadeRepository;

    public AvaliacaoConformidade salvar(AvaliacaoConformidade avaliacaoConformidade){
        return avaliacaoConformidadeRepository.save(avaliacaoConformidade);
    }

    public List<AvaliacaoConformidade> obterAvaliacoesFornecedor(UUID idFornecedor){
        return avaliacaoConformidadeRepository.obterAvaliacoesFornecedor(idFornecedor);
    }

    public AvaliacaoConformidade obter(UUID id) {
        return getAvaliacaoConformidade(id);
    }

    private AvaliacaoConformidade getAvaliacaoConformidade(UUID id){
        return avaliacaoConformidadeRepository.findById(id)
                .orElseThrow(()->new RecursoNaoEncontradoException("Avaliação não encontrada"));
    }
}
