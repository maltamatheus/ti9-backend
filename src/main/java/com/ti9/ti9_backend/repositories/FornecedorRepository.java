package com.ti9.ti9_backend.repositories;

import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    Page<Fornecedor> obterFornecedores(String nome,
                                       String cnpj,
                                       String segmento,
                                       EnumCategoriaRisco categoriaRisco,
                                       Boolean ativo,
                                       Pageable pageable);

    @Query("select f.ativo, count(f.razaoSocial) " +
            "from Fornecedor f " +
            "where 1 = 1 " +
            "group by f.razaoSocial,f.ativo " +
            "order by f.ativo")
    List<Object[]> obterResumoAtivosInativos();
    @Query("select coalesce(f.categoriaRisco,'BLANK') , count(f.razaoSocial) " +
            "from Fornecedor f " +
            "where 1 = 1 " +
            "group by f.razaoSocial,f.categoriaRisco " +
            "order by f.razaoSocial,f.categoriaRisco")
    List<Object[]> obterResumoCategoriasRisco();
    @Query("select coalesce(upper(f.segmento),'BLANK') , count(f.id) " +
            "from Fornecedor f " +
            "where 1 = 1 " +
            "group by f.razaoSocial, f.segmento")
    List<Object[]> obterResumoSegmento();
    @Query("select f.razaoSocial, coalesce(upper(f.segmento),'BLANK'), " +
            "sum(f.pontuacaoConformidade) " +
            "from Fornecedor f " +
            "where 1 = 1 " +
            "group by f.razaoSocial, f.segmento " +
            "order by f.razaoSocial,f.segmento")
    List<Object[]> obterPontuacaoSegmento();
    @Query("select f.razaoSocial, sum(f.pontuacaoConformidade) " +
            "from Fornecedor f " +
            "where 1 = 1 " +
            "group by f.razaoSocial")
    List<Object[]> obterPontuacao();

}
