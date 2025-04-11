package com.rpps.beneficios.controller;


import com.rpps.beneficios.DTO.CriarSolicitacaoBeneficioDTO;
import com.rpps.beneficios.DTO.SolicitacaoBeneficioDTO;
import com.rpps.beneficios.DTO.TotalBeneficiosPorCpfDTO;
import com.rpps.beneficios.model.SolicitacaoBeneficio;
import com.rpps.beneficios.service.SolicitacaoBeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/beneficios/solicitacao")

public class SolicitacaoBeneficioController {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private SolicitacaoBeneficioService solicitacaoBeneficioService;


    // endpoint para criar solicitação e já analisá-las
    @PostMapping
    public ResponseEntity<SolicitacaoBeneficioDTO> criarSolicitacao(@RequestBody CriarSolicitacaoBeneficioDTO dto) {
        SolicitacaoBeneficioDTO resposta = solicitacaoBeneficioService.criarSolicitacao(dto);
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



    // listar todos os beneficios de determinado cpf
    @GetMapping("/cpf/{cpf}/total")
    public ResponseEntity<TotalBeneficiosPorCpfDTO> calcularTotalPorCpf(@PathVariable String cpf) {
        TotalBeneficiosPorCpfDTO dto = solicitacaoBeneficioService.calcularTotalDeBeneficiosPorCpf(cpf);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("")
    public ResponseEntity<List<SolicitacaoBeneficioDTO>> listarSolicitacoesAtivas() {
        List<SolicitacaoBeneficioDTO> dtos = solicitacaoBeneficioService.listarSolicitacoesAtivas()
                .stream()
                .map(s -> new SolicitacaoBeneficioDTO(
                        s.getCpf(),
                        s.getTempoContribuicaoCalculado(),
                        s.getValorConcedido(),
                        s.getStatus(),
                        s.getMensagem(),
                        s.getTotalBeneficios(),
                        s.getTipoBeneficio(),
                        s.isAtivo()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }





}



