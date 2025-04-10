package com.rpps.beneficios.controller;


import com.rpps.beneficios.DTO.CriarSolicitacaoBeneficioDTO;
import com.rpps.beneficios.DTO.SolicitacaoBeneficioDTO;
import com.rpps.beneficios.model.SolicitacaoBeneficio;
import com.rpps.beneficios.service.SolicitacaoBeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rpps.beneficios.DTO.AnaliseSoliticacaoBeneficioDTO;
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


    // endpoint para criar solicitação
    @PostMapping("/{id}")
    public ResponseEntity<SolicitacaoBeneficioDTO> criarSolicitacao(@RequestBody CriarSolicitacaoBeneficioDTO dto) {
        SolicitacaoBeneficio criada = solicitacaoBeneficioService.criarSolicitacao(dto.getCpf(), dto.getBeneficioId());

        SolicitacaoBeneficioDTO resposta = new SolicitacaoBeneficioDTO(
                criada.getCpf(),
                criada.getStatus(),
                criada.getTipoBeneficio()
        );

        return ResponseEntity.status(201).body(resposta);
    }

    // para listar solicitação
    @GetMapping
    public ResponseEntity<List<SolicitacaoBeneficioDTO>> listarSolicitacoes() {
        List<SolicitacaoBeneficioDTO> dtos = solicitacaoBeneficioService
                .listarSolicitacoesAtivas()
                .stream()
                .map(s -> new SolicitacaoBeneficioDTO(
                        s.getCpf(),
                        s.getStatus(),
                        s.getTipoBeneficio()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos); // status 200 OK
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


    @GetMapping("/analise/{cpf}/{beneficioId}")
    public ResponseEntity<AnaliseSoliticacaoBeneficioDTO> analisarSolicitacao(
            @PathVariable String cpf,
            @PathVariable int beneficioId) {

        AnaliseSoliticacaoBeneficioDTO resultado = solicitacaoBeneficioService.analisarSolicitacao(cpf, beneficioId);
        return ResponseEntity.ok(resultado);
    }



    // endpoint para ver todods os benficios de determinado cpf
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<SolicitacaoBeneficioDTO>> listarPorCpf(@PathVariable String cpf) {
        List<SolicitacaoBeneficio> solicitacoes = solicitacaoBeneficioService.listarSolicitacoesPorCpf(cpf);

        List<SolicitacaoBeneficioDTO> dtos = solicitacoes.stream().map(s -> new SolicitacaoBeneficioDTO(
                s.getCpf(),
                s.getStatus(),
                s.getTipoBeneficio()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


















}



 // vou tbm  precisar de um endpoint para calcular o beneficio
//    //  Novo endpoint para verificar o direito ao benefício
//    @GetMapping("/verificar/{cpf}/{beneficioId}")
//    public SolicitacaoBeneficioDTO verificarDireitoBeneficio(@PathVariable String cpf, @PathVariable int beneficioId) {
//        return solicitacaoBeneficioService.verificarDireitoBeneficio(cpf, beneficioId);
//    }
//}
