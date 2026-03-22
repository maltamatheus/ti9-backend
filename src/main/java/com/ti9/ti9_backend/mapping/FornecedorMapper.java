package com.ti9.ti9_backend.mapping;

import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.mapping.dto.FornecedorUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface FornecedorMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "dataCadastro",ignore=true)
    @Mapping(target = "dataUltimaAtualizacao", expression = "java(java.time.LocalDateTime.now())")
    void updateFornecedorFromDto(FornecedorUpdateDto fornecedorUpdateDto, @MappingTarget Fornecedor fornecedor);
}
