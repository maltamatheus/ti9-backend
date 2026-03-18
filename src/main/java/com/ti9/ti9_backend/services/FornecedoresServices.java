package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import com.ti9.ti9_backend.mapping.FornecedorMapper;
import com.ti9.ti9_backend.mapping.dto.FornecedorUpdateDto;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FornecedoresServices {
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private FornecedorMapper fornecedorMapper;
    public Fornecedor criarFornecedor(Fornecedor fornecedor){
        return salvaFornecedor(fornecedor);
    }

    public Fornecedor obterFornecedor(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
    }

    public Page<Fornecedor> listarFornecedores(String nome,
                                               String cnpj,
                                               String segmento,
                                               EnumCategoriaRisco categoriaRisco,
                                               Boolean ativo,
                                               Pageable pageable) {

        return fornecedorRepository.getFornecedores(nome,
                                                    cnpj,
                                                    segmento,
                                                    categoriaRisco,
                                                    ativo,
                                                    pageable);
    }

    public Fornecedor atualizarFornecedor(FornecedorUpdateDto fornecedorUpdateDto){
        Fornecedor baseFornecedor = obterFornecedor(fornecedorUpdateDto.getId());
        fornecedorMapper.updateFornecedorFromDto(fornecedorUpdateDto,baseFornecedor);
        return salvaFornecedor(baseFornecedor);
    }

    private Fornecedor salvaFornecedor(Fornecedor fornecedor){
        try {
            return fornecedorRepository.save(fornecedor);
        } catch (Exception e) {
            throw new ValorNaoPermitidoException(e.getMessage());
        }
    }

    public Fornecedor ativarDesativarFornecedor(UUID id) {
        Fornecedor baseFornecedor = obterFornecedor(id);
        baseFornecedor.setAtivo(!baseFornecedor.getAtivo());
        return salvaFornecedor(baseFornecedor);
    }

    public Fornecedor excluir(UUID id) {
        Fornecedor baseFornecedor = obterFornecedor(id);
        baseFornecedor.setAtivo(false);
        return salvaFornecedor(baseFornecedor);
    }
}
