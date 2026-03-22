package com.ti9.ti9_backend.mapping;

import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.mapping.dto.DocumentoUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface DocumentoMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "dataUpload",ignore = true)
    @Mapping(source = "idFornecedor", target = "fornecedor.id",ignore = true)
    void updateDocumentoFromDto(DocumentoUpdateDto documentoUpdateDto, @MappingTarget Documento documento);
}
