package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.domains.dtos.entities.AvaliacaoConformidadeDto;
import com.ti9.ti9_backend.domains.dtos.entities.DocumentoDto;
import com.ti9.ti9_backend.domains.dtos.entities.FornecedorDto;
import com.ti9.ti9_backend.domains.dtos.responses.FornecedorResponseDto;
import com.ti9.ti9_backend.domains.entities.AvaliacaoConformidade;
import com.ti9.ti9_backend.domains.entities.Documento;
import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.mapping.dto.FornecedorUpdateDto;
import com.ti9.ti9_backend.services.FornecedoresServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fornecedores")
@AllArgsConstructor
public class FornecedoresController {
    private FornecedoresServices fornecedoresServices;
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> obterFornecedor(@PathVariable UUID id){
        return ResponseEntity.ok(fornecedoresServices.obterFornecedor(id));
    }
    @ResponseBody
    @GetMapping
    public ResponseEntity<Page<Fornecedor>> obterFornecedores(@RequestParam(required = false) String nome,
                                                              @RequestParam(required = false) String cnpj,
                                                              @RequestParam(required = false) String segmento,
                                                              @RequestParam(required = false) EnumCategoriaRisco categoriaRisco,
                                                              @RequestParam(required = false) Boolean ativo,
                                                              @PageableDefault(size=10,sort = "razaoSocial") Pageable pageable){


        return ResponseEntity.ok(fornecedoresServices.obterFornecedores(nome,cnpj,segmento,categoriaRisco,ativo,pageable));
    }
    @ResponseBody
    @PostMapping
    public ResponseEntity<Fornecedor> criarFornecedor(@Valid @RequestBody FornecedorDto fornecedorDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedoresServices.criarFornecedor(fornecedorDto));
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @Valid @RequestBody FornecedorUpdateDto fornecedorUpdateDto){
        return ResponseEntity.status(HttpStatus.OK).body(fornecedoresServices.atualizarFornecedor(id,fornecedorUpdateDto));
    }
    @ResponseBody
    @PatchMapping("/{id}/status")
    public ResponseEntity<Fornecedor> ativarDesativarFornecedor(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(fornecedoresServices.ativarDesativarFornecedor(id));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Fornecedor> excluir(@PathVariable UUID id){ // Exclusão Lógica
        return ResponseEntity.ok(fornecedoresServices.excluir(id));
    }

    @ResponseBody
    @PostMapping("/{id}/documentos")
    public ResponseEntity<?> adicionarDocumento(@PathVariable UUID id, @Valid @RequestBody DocumentoDto documentoDto){
        documentoDto.setIdFornecedor(id);
        return ResponseEntity.ok(fornecedoresServices.adicionarDocumento(documentoDto));
    }
    @ResponseBody
    @GetMapping("/{id}/documentos")
    public ResponseEntity<Page<Documento>> obterDocumentos(@PathVariable UUID id,
                                                           @PageableDefault(page=0,size=10) Pageable pageable){
        return ResponseEntity.ok(fornecedoresServices.obterDocumentos(id,pageable));
    }
    @ResponseBody
    @PostMapping("/{id}/avaliacoes")
    public ResponseEntity<?> adicionarAvaliacao(@PathVariable UUID id, @RequestBody @Valid AvaliacaoConformidadeDto avaliacaoConformidadeDto){
        avaliacaoConformidadeDto.setIdFornecedor(id);
        return ResponseEntity.ok(fornecedoresServices.adicionarAvaliacao(avaliacaoConformidadeDto));
    }
    @PostMapping(value="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<List<Fornecedor>> uploadFornecedores(@RequestPart("file") MultipartFile file){
        return ResponseEntity.ok(fornecedoresServices.uploadFornecedoresFile(file));
    }
}
