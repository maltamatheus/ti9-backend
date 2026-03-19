package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.dtos.ResumoDto;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServices {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public ResumoDto obterResumoAtivosInativos(){
        Map<Boolean,Long> groupAtivosInativos = new HashMap<>();
        List<Object[]> rows = fornecedorRepository.obterResumoAtivosInativos();
        for(Object[] row : rows){
            groupAtivosInativos.put((Boolean) row[0],(Long) row[1]);
        }
        return ResumoDto.builder()
                .groupAtivosInativos(groupAtivosInativos)
                .build();
    }
}
