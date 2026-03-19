package com.ti9.ti9_backend.repositories;

import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoConformidadeRepository extends JpaRepository<AvaliacaoConformidade, UUID>{

    @Query("select a " +
            "from AvaliacaoConformidade a " +
            "where 1 = 1 " +
            "and a.fornecedor.id = :idFornecedor " )
    List<AvaliacaoConformidade> obterAvaliacoesFornecedor(UUID idFornecedor);
}
