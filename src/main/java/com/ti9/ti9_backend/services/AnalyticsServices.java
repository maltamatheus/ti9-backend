package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.dtos.ConformidadeAnalyticDto;
import com.ti9.ti9_backend.domains.dtos.ResumoAnalyticDto;
import com.ti9.ti9_backend.repositories.DocumentoRepository;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServices {
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private DocumentoRepository documentoRepository;

    public ResumoAnalyticDto obterResumos(){
        //Resumo - Ativos e Inativos
        Map<Boolean,Long> groupAtivosInativos = new HashMap<>();
        List<Object[]> rows = fornecedorRepository.obterResumoAtivosInativos();
        for(Object[] row : rows){
            groupAtivosInativos.put((Boolean) row[0],(Long) row[1]);
        }

        //Resumo - Categorias Risco
        Map<String,Long> groupCategoriaRisco = new HashMap<>();
        List<Object[]> rows01 = fornecedorRepository.obterResumoCategoriasRisco();
        for(Object[] row : rows01){
            groupCategoriaRisco.put((String) row[0],(Long) row[1]);
        }

        //Resumo - Segmento
        Map<String,Long> groupSegmento = new HashMap<>();
        List<Object[]> rows02 = fornecedorRepository.obterResumoSegmento();
        for(Object[] row : rows02){
            groupCategoriaRisco.put((String) row[0],(Long) row[1]);
        }

        //Construindo o Retorno
        return ResumoAnalyticDto.builder()
                .groupAtivosInativos(groupAtivosInativos)
                .groupCategoriasRisco(groupCategoriaRisco)
                .groupSegmentos(groupSegmento)
                .documentosVencidos(documentoRepository.obterResumoDocumentosVencidos(LocalDate.now()))
                .build();
    }

    public ConformidadeAnalyticDto obterConformidades(){
        return ConformidadeAnalyticDto.builder()
                .mediaPontuacaoSegmento(getMediaPontuacaoSegmento())
                .build();
    }

    private Map<String, Map<String, Double>> getMediaPontuacaoSegmento() {
        //Media Pontuacao Segmento
        Map<String,Long> pontuacao = new LinkedHashMap<>();
        Map<String,String> fornecedorSegmentoKey = new LinkedHashMap<>();
        Map<Map<String,String>,Long> pontuacaoSegmento = new LinkedHashMap<>();
        Map<String,Map<String,Double>> mediaPontuacaoSegmento = new LinkedHashMap<>();

        //Carregando a Pontuacao por Fornecedor
        List<Object[]> pontuacaoFornecedores = fornecedorRepository.obterPontuacao();
        for (Object[] pontuacaoFornecedor : pontuacaoFornecedores){
            pontuacao.put((String)pontuacaoFornecedor[0],(Long) pontuacaoFornecedor[1]);
        }

        //Carregando a Pontuacao por Segmento de cada Fornecedor
        List<Object[]> rowsPontuacaoSegmento = fornecedorRepository.obterPontuacaoSegmento();
        for(Object[] rowPontuacaoSegmento : rowsPontuacaoSegmento) {
            fornecedorSegmentoKey.put((String) rowPontuacaoSegmento[0], (String) rowPontuacaoSegmento[1]);
            pontuacaoSegmento.put(fornecedorSegmentoKey, (Long) rowPontuacaoSegmento[2]);
        }

        for(Map.Entry<String,String> fornecedorSegmento: fornecedorSegmentoKey.entrySet()){
            String fornecedor = fornecedorSegmento.getKey();
            String segmento = fornecedorSegmentoKey.get(fornecedorSegmento);
            Long pontosPorSegmento = pontuacaoSegmento.get(fornecedorSegmento);
            Long totalPontosFornecedor = pontuacao.get(fornecedorSegmento.getKey());
            Map<String,Double> pontuacaoMediaPorSegmento = new LinkedHashMap<>();
            pontuacaoMediaPorSegmento.put(segmento,Double.valueOf(pontosPorSegmento/totalPontosFornecedor));
            mediaPontuacaoSegmento.put(fornecedor,pontuacaoMediaPorSegmento);
        }
        return mediaPontuacaoSegmento;
    }

}
