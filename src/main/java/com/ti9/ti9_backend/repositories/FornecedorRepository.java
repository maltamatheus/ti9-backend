package com.ti9.ti9_backend.repositories;

import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID>{

    @Query("select f " +
            "from Fornecedor f " +
            "where 1 = 1 " +
            "and (f.razaoSocial like %:nome% or :nome is null) " +
            "and (f.cnpj = :cnpj or :cnpj is null) " +
            "and (f.segmento = :segmento or :segmento is null) " +
            "and (f.categoriaRisco = :categoriaRisco or :categoriaRisco is null) " +
            "and (f.ativo = :ativo or :ativo is null) " )
    Page<Fornecedor> getFornecedores(String nome,
                                     String cnpj,
                                     String segmento,
                                     EnumCategoriaRisco categoriaRisco,
                                     Boolean ativo,
                                     Pageable pageable);
}
