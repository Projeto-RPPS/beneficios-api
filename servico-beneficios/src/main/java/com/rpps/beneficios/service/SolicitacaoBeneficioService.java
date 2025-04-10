package com.rpps.beneficios.service;

import com.rpps.beneficios.DTO.AnaliseSoliticacaoBeneficioDTO;
import com.rpps.beneficios.DTO.ContribuicaoDTO;
import com.rpps.beneficios.DTO.SolicitacaoBeneficioDTO;
import com.rpps.beneficios.model.Beneficio;
import com.rpps.beneficios.model.SolicitacaoBeneficio;
import com.rpps.beneficios.repository.SolicitacaoBeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.rpps.beneficios.repository.BeneficioRepository;

import java.util.Arrays;
import java.util.Optional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class SolicitacaoBeneficioService {




    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private SolicitacaoBeneficioRepository solicitacaoBeneficioRepository;


    // Utilizando RestTemplate para fazer a requisição HTTP (de outras APIs)
    @Autowired
    private RestTemplate restTemplate;


    // cria solicitação
    public SolicitacaoBeneficio criarSolicitacao(String cpf, int beneficioId) {
        Optional<Beneficio> beneficioOpt = beneficioRepository.findById(beneficioId);

        if (beneficioOpt.isEmpty()) {
            throw new RuntimeException("Benefício não encontrado");
        }

        Beneficio beneficio = beneficioOpt.get();

        SolicitacaoBeneficio solicitacao = new SolicitacaoBeneficio();
        solicitacao.setCpf(cpf);
        solicitacao.setTipoBeneficio(beneficio.getTipo());
        solicitacao.setStatus("ativo");
        solicitacao.setMensagem("Solicitação criada com sucesso");
        solicitacao.setAtivo(true);

        return solicitacaoBeneficioRepository.save(solicitacao);
    }

    // lista solicitação
    public List<SolicitacaoBeneficio> listarSolicitacoesAtivas() {
        return solicitacaoBeneficioRepository.findAll().stream()
                .filter(SolicitacaoBeneficio::isAtivo)
                .collect(Collectors.toList());
    }


    public boolean desativarSolicitacao(int id) {
        return solicitacaoBeneficioRepository.findById(id).map(solicitacao -> {
            solicitacao.setAtivo(false);
            solicitacao.setMensagem("Solicitação de benefício desativada");
            solicitacaoBeneficioRepository.save(solicitacao);
            return true;
        }).orElse(false);
    }

    // analisar solicitação de beneficio

    public AnaliseSoliticacaoBeneficioDTO analisarSolicitacao(String cpf, int beneficioId) {
        Optional<Beneficio> beneficioOpt = beneficioRepository.findById(beneficioId);

        if (beneficioOpt.isEmpty()) {
            return new AnaliseSoliticacaoBeneficioDTO(cpf, 0, false, 0.0, "Benefício não encontrado");
        }

        Beneficio beneficio = beneficioOpt.get();
        int tempoMinimo = beneficio.getTempoMinimoMeses();
        double percentual = beneficio.getPercentualBaseMedioContribuicoes();

        try {
            // Chamada para API do Pedro
            String url = "http://localhost:8083/contribuicoes/cpf/" + cpf;
            ResponseEntity<ContribuicaoDTO[]> response = restTemplate.getForEntity(url, ContribuicaoDTO[].class);

            if (response.getBody() == null || response.getBody().length == 0) {
                return new AnaliseSoliticacaoBeneficioDTO(cpf, 0, false, 0.0, "Nenhuma contribuição encontrada");
            }

            List<ContribuicaoDTO> contribuicoes = Arrays.asList(response.getBody());

            int totalMeses = contribuicoes.size();
            double mediaContribuicao = contribuicoes.stream()
                    .mapToDouble(ContribuicaoDTO::getValorContribuicao)
                    .average()
                    .orElse(0.0);

            if (totalMeses >= tempoMinimo) {
                double valorConcedido = (percentual / 100.0) * mediaContribuicao;
                return new AnaliseSoliticacaoBeneficioDTO(cpf, totalMeses, true, valorConcedido, "Benefício concedido");
            } else {
                return new AnaliseSoliticacaoBeneficioDTO(cpf, totalMeses, false, 0.0, "Contribuição insuficiente");
            }

        } catch (Exception e) {
            return new AnaliseSoliticacaoBeneficioDTO(cpf, 0, false, 0.0, "Erro ao consultar contribuições");
        }



    }


// listar beneficio por cpf
    public List<SolicitacaoBeneficio> listarSolicitacoesPorCpf(String cpf) {
        return solicitacaoBeneficioRepository.findByCpf(cpf);
    }

























}