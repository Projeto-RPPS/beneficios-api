package com.rpps.beneficios.controller;


import com.rpps.beneficios.DTO.CriarSolicitacaoBeneficioDTO;
import com.rpps.beneficios.DTO.RetornarSolicitacaoBeneficioDTO;
import com.rpps.beneficios.DTO.TotalBeneficiosPorCpfDTO;
import com.rpps.beneficios.model.SolicitacaoBeneficio;
import com.rpps.beneficios.repository.SolicitacaoBeneficioRepository;
import com.rpps.beneficios.service.SolicitacaoBeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/beneficios/solicitacao")

public class SolicitacaoBeneficioController {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private SolicitacaoBeneficioService solicitacaoBeneficioService;


    // endpoint para criar solicitação e já analisá-las
    @PostMapping
    public ResponseEntity<RetornarSolicitacaoBeneficioDTO> criarSolicitacao(@RequestBody CriarSolicitacaoBeneficioDTO dto) {
        RetornarSolicitacaoBeneficioDTO resposta = solicitacaoBeneficioService.criarSolicitacao(dto);
        return ResponseEntity.status(201).body(resposta);
    }



    // Para desativar logicamente um beneficio
    @PatchMapping("/desativar/{id}")
    public ResponseEntity<String> desativarSolicitacao(@PathVariable int id) {
        boolean resultado = solicitacaoBeneficioService.desativarSolicitacao(id);

        if (resultado) {
            return ResponseEntity.ok("Solicitação de benefício desativada com sucesso");
        } else {
            return ResponseEntity.status(404).body("Solicitação não encontrada");
        }

    }

    // listar solicitacoes
    @GetMapping
    public ResponseEntity<List<SolicitacaoBeneficio>> listarTodasSolicitacoes() {
        List<SolicitacaoBeneficio> solicitacoes = solicitacaoBeneficioService.listarTodas();
        return ResponseEntity.ok(solicitacoes);
    }


    @GetMapping("/ativas")
    public ResponseEntity<List<SolicitacaoBeneficio>> listarSolicitacoesAtivas() {
        List<SolicitacaoBeneficio> lista = solicitacaoBeneficioService.listarAtivas();
        return ResponseEntity.ok(lista);
    }




    // listar todos os beneficios de determinado cpf
    @GetMapping("/cpf/{cpf}/total")
    public ResponseEntity<TotalBeneficiosPorCpfDTO> calcularTotalPorCpf(@PathVariable String cpf) {
        TotalBeneficiosPorCpfDTO dto = solicitacaoBeneficioService.calcularTotalDeBeneficiosPorCpf(cpf);
        return ResponseEntity.ok(dto);
    }





}



