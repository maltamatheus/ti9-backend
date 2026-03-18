package com.ti9.ti9_backend.controllers;

import com.ti9.ti9_backend.domains.entities.Fornecedor;
import com.ti9.ti9_backend.domains.enums.EnumCategoriaRisco;
import com.ti9.ti9_backend.mapping.dto.FornecedorUpdateDto;
import com.ti9.ti9_backend.services.FornecedoresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/fornecedores")
public class FornecedoresController {
    @Autowired
    private FornecedoresServices fornecedoresServices;
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> obterFornecedor(@PathVariable UUID id){
        return ResponseEntity.ok(fornecedoresServices.obterFornecedor(id));
    }
    @ResponseBody
    @GetMapping
    public ResponseEntity<Page<Fornecedor>> listarFornecedores(@RequestParam(required = false) String nome,
                                                            @RequestParam(required = false) String cnpj,
                                                            @RequestParam(required = false) String segmento,
                                                            @RequestParam(required = false) EnumCategoriaRisco categoriaRisco,
                                                            @RequestParam(required = false) Boolean ativo,
                                                            @PageableDefault(size=10,sort = "razaoSocial") Pageable pageable){

        return ResponseEntity.ok(fornecedoresServices.listarFornecedores(nome,cnpj,segmento,categoriaRisco,ativo,pageable));
    }
    @ResponseBody
    @PostMapping
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody Fornecedor fornecedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedoresServices.criarFornecedor(fornecedor));
    }

    @ResponseBody
    @PutMapping
    public ResponseEntity<Fornecedor> atualizarFornecedor(@RequestBody FornecedorUpdateDto fornecedorUpdateDto){
        return ResponseEntity.status(HttpStatus.OK).body(fornecedoresServices.atualizarFornecedor(fornecedorUpdateDto));
    }
    @ResponseBody
    @PatchMapping("/{id}/status")
    public ResponseEntity<Fornecedor> ativarDesativarFornecedor(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(fornecedoresServices.ativarDesativarFornecedor(id));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Fornecedor> excluir(@PathVariable UUID id){
        return ResponseEntity.ok(fornecedoresServices.excluir(id));
    }
}
