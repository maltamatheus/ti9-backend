package com.ti9.ti9_backend.repositories;

import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, UUID>{

    @Query("select d " +
            "from Documento d " +
            "where 1 = 1 " +
            "and d.fornecedor.id = :idFornecedor" )
    List<Documento> obterDocumentosFornecedor(UUID idFornecedor);
    @Query("select d " +
            "from Documento d " +
            "where 1 = 1 " +
            "and d.fornecedor.id = :idfornecedor" )
    Page<Documento> obterDocumentosFornecedor(UUID idfornecedor,Pageable pageable);

    @Query("select d " +
            "from Documento d " +
            "where 1 = 1 " +
            "and d.dataValidade < :dataVencto")
    List<Documento> obterVencidos(LocalDate dataVencto);
    @Query("select count(d.id) " +
            "from Documento d " +
            "where 1 = 1 " +
            "and d.dataValidade < :dataVencto")
    Long obterResumoDocumentosVencidos(LocalDate dataVencto);
}
