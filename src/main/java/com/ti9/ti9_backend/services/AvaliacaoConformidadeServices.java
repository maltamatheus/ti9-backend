package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.repositories.AvaliacaoConformidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AvaliacaoConformidadeServices {
    @Autowired
    private AvaliacaoConformidadeRepository avaliacaoConformidadeRepository;

    public List<AvaliacaoConformidade> obterAvaliacoesFornecedor(UUID idFornecedor){
        return avaliacaoConformidadeRepository.obterAvaliacoesFornecedor(idFornecedor);
    }
}
