package com.ti9.ti9_backend.services;

import com.ti9.ti9_backend.domains.dtos.responses.ConformidadeAnalyticResponseDto;
import com.ti9.ti9_backend.domains.dtos.responses.ResumoAnalyticResponseDto;
import com.ti9.ti9_backend.domains.dtos.responses.StatusDocumentosAnalyticResponseDto;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.domains.enums.EnumStatus;
import com.ti9.ti9_backend.repositories.AvaliacaoConformidadeRepository;
import com.ti9.ti9_backend.repositories.DocumentoRepository;
import com.ti9.ti9_backend.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private AvaliacaoConformidadeRepository avaliacaoConformidadeRepository;

    public ResumoAnalyticResponseDto obterResumos(){
        return ResumoAnalyticResponseDto.builder()
                .groupAtivosInativos(getAtivosInativos())
                .groupCategoriasRisco(getCategoriasRisco())
                .groupSegmentos(getSegmento())
                .documentosVencidos(getTotalDocumentosVencidos())
                .build();
    }
    public ConformidadeAnalyticResponseDto obterConformidades(){
        ConformidadeAnalyticResponseDto res = ConformidadeAnalyticResponseDto.builder()
                .mediaPontuacaoSegmento(getMediaPontuacaoSegmento())
                .evolucaoTemporalFornecedor(getEvolucaoTemporalFornecedor())
                .melhores(getMelhores())
                .piores(getPiores())
                .build();

        return res;
    }
    private Long getTotalDocumentosVencidos() {
        return documentoRepository.obterResumoDocumentosVencidos(LocalDate.now());
    }
    private Map<String, Long> getSegmento() {
        //Resumo - Segmento
        Map<String,Long> groupSegmento = new HashMap<>();
        List<Object[]> rows = fornecedorRepository.obterResumoSegmento();
        for(Object[] row : rows){
            groupSegmento.put((String) row[0],(Long) row[1]);
        }
        return groupSegmento;
    }
    private Map<EnumCategoriaRisco, Long> getCategoriasRisco() {
        //Resumo - Categorias Risco
        Map<EnumCategoriaRisco,Long> groupCategoriaRisco = new HashMap<>();
        List<Object[]> rows = fornecedorRepository.obterResumoCategoriasRisco();
        for(Object[] row : rows){
            groupCategoriaRisco.put((EnumCategoriaRisco) row[0],(Long) row[1]);
        }
        return groupCategoriaRisco;
    }
    private Map<Boolean, Long> getAtivosInativos() {
        //Resumo - Ativos e Inativos
        Map<Boolean,Long> groupAtivosInativos = new HashMap<>();
        List<Object[]> rows = fornecedorRepository.obterResumoAtivosInativos();
        for(Object[] row : rows){
            groupAtivosInativos.put((Boolean) row[0],(Long) row[1]);
        }
        return groupAtivosInativos;
    }
    private Map<String, Long> getMelhores() {
        Map<String,Long> melhores = new LinkedHashMap<>();
        List<Object[]> melhoresRows = avaliacaoConformidadeRepository.obterMelhores(PageRequest.of(0,10));
        for (Object[] row : melhoresRows){
            melhores.put((String) row[0],(Long) row[1]);
        }
        return melhores;
    }
    private Map<String, Long> getPiores() {
        Map<String,Long> piores = new LinkedHashMap<>();
        List<Object[]> pioresRows = avaliacaoConformidadeRepository.obterPiores(PageRequest.of(0,10));
        for (Object[] row : pioresRows){
            piores.put((String) row[0],(Long) row[1]);
        }
        return piores;
    }
    private Map<String,Map<LocalDateTime,Long>> getEvolucaoTemporalFornecedor() {
        List<Object[]> evolucaoTemporal = avaliacaoConformidadeRepository.obterEvolucaoTemporalConformidade();
        Map<String,Map<LocalDateTime,Long>> evolucaoTemporalFornecedor = new LinkedHashMap<>();
        for (Object[] row:evolucaoTemporal){
            String fornecedor = (String) row[0];
            LocalDateTime dataAvaliacao = (LocalDateTime)  row[1];
            Long pontuacaoTotal = (Long) row[2];
            Map<LocalDateTime,Long> pontuacaoPorDataAvaliacao = new LinkedHashMap<>();
            pontuacaoPorDataAvaliacao.put(dataAvaliacao,pontuacaoTotal);
            evolucaoTemporalFornecedor.put(fornecedor,pontuacaoPorDataAvaliacao);
        }
        return evolucaoTemporalFornecedor;
    }
    private Map<String, Map<String, Double>> getMediaPontuacaoSegmento() {
        //Media Pontuacao Segmento
        Map<String,Long> pontuacaoFornecedores = new LinkedHashMap<>();
        Map<String,String> fornecedorSegmentoKey = new LinkedHashMap<>();
        Map<Map<String,String>,Long> pontuacaoSegmento = new LinkedHashMap<>();
        Map<String,Map<String,Double>> mediaPontuacaoSegmento = new LinkedHashMap<>();

        //Carregando a Pontuacao por Fornecedor
        List<Object[]> qrypontuacaoFornecedores = fornecedorRepository.obterPontuacao();
        for (Object[] pontuacaoFornecedor : qrypontuacaoFornecedores){
            Long ptoFornecedor = (Long) pontuacaoFornecedor[1] == null ? 0 : (Long) pontuacaoFornecedor[1];
            pontuacaoFornecedores.put((String)pontuacaoFornecedor[0],ptoFornecedor);
        }

        //Carregando a Pontuacao de cada Fornecedor por Segmento
        List<Object[]> rowsPontuacaoSegmento = fornecedorRepository.obterPontuacaoSegmento();
        for(Object[] rowPontuacaoSegmento : rowsPontuacaoSegmento) {
            fornecedorSegmentoKey.put((String) rowPontuacaoSegmento[0], (String) rowPontuacaoSegmento[1]);
            Long ptoSegmento = (Long) rowPontuacaoSegmento[2] == null ? 0 : (Long) rowPontuacaoSegmento[2];
            pontuacaoSegmento.put(fornecedorSegmentoKey, (Long) rowPontuacaoSegmento[2]);
        }

        for(Map.Entry<String,String> fornecedorSegmento: fornecedorSegmentoKey.entrySet()){
            String fornecedor = fornecedorSegmento.getKey();
            String segmento = fornecedorSegmentoKey.get(fornecedor);
            Long pontosPorSegmento = pontuacaoSegmento.get(fornecedorSegmento) == null ? 0 : pontuacaoSegmento.get(fornecedorSegmento) ;
            Long totalPontosFornecedor = pontuacaoFornecedores.get(fornecedorSegmento.getKey()) == null ? 0 : pontuacaoFornecedores.get(fornecedorSegmento.getKey());
            Map<String,Double> pontuacaoMediaPorSegmento = new LinkedHashMap<>();
            try{
                pontuacaoMediaPorSegmento.put(segmento,Double.valueOf(pontosPorSegmento/totalPontosFornecedor));
            } catch (ArithmeticException exception){
                pontuacaoMediaPorSegmento.put(segmento,Double.valueOf(0));
            }
            mediaPontuacaoSegmento.put(fornecedor,pontuacaoMediaPorSegmento);
        }
        return mediaPontuacaoSegmento;
    }

    public StatusDocumentosAnalyticResponseDto obterStatusDocumentos(){
        Map<String,Long> statusDocumentos = new LinkedHashMap<>();

        List<Object[]> rows = documentoRepository.obterStatusDocumentos();
        for(Object[] row : rows){
            EnumStatus statusRetornado = (EnumStatus) row[0];
            Long qtde = (Long) row[1] == null ? 0 : (Long) row[1];
            statusDocumentos.put(statusRetornado.name(),qtde);
        }

        return StatusDocumentosAnalyticResponseDto.builder()
                .statusDocumentos(statusDocumentos)
                .build();
    }
}
