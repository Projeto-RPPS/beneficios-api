package com.rpps.beneficios.service;

import com.rpps.beneficios.DTO.*;
import com.rpps.beneficios.model.Beneficio;
import com.rpps.beneficios.model.SolicitacaoBeneficio;
import com.rpps.beneficios.repository.SolicitacaoBeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.rpps.beneficios.repository.BeneficioRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;


import java.util.Arrays;
import java.util.Optional;


import java.util.List;
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


    // cria solicitação e já fazer a analise da solicitado do beneficio
    public RetornarSolicitacaoBeneficioDTO criarSolicitacao(CriarSolicitacaoBeneficioDTO dto) {
        String cpf = dto.getCpf();
        int beneficioId = dto.getBeneficioId();

        // Busca o benefício pelo ID
        Optional<Beneficio> beneficioOpt = beneficioRepository.findById(beneficioId);
        if (beneficioOpt.isEmpty()) {
            throw new RuntimeException("Benefício não encontrado");
        }

        Beneficio beneficio = beneficioOpt.get();

        try {
            // Chamada para API de Contribuições (Pedro)
            String url = "http://rpps_api:8084/contribuicoes/cpf/" + cpf;
            ResponseEntity<ContribuicaoDTO[]> response = restTemplate.getForEntity(url, ContribuicaoDTO[].class);

//            HttpEntityConect api_pedro = new 8888(host, port)
//            var res = api_pedro.connect;


            List<ContribuicaoDTO> contribuicoes = Arrays.asList(response.getBody());

            int totalMeses = contribuicoes.size();
            BigDecimal soma = contribuicoes.stream()
                    .map(ContribuicaoDTO::getValorContribuicao)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            BigDecimal mediaContribuicao = totalMeses > 0
                    ? soma.divide(BigDecimal.valueOf(totalMeses), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            boolean concedido = totalMeses >= beneficio.getTempoMinimoMeses();
            String status = concedido ? "ativo" : "inativo";
            String mensagem = concedido ? "Benefício concedido com sucesso" : "Contribuição insuficiente";

            BigDecimal percentual = BigDecimal.valueOf(beneficio.getPercentualBaseMedioContribuicoes()).divide(BigDecimal.valueOf(100));
            BigDecimal valorConcedido = concedido ? percentual.multiply(mediaContribuicao) : BigDecimal.ZERO;

            // Cria e salva a solicitação no banco
            SolicitacaoBeneficio solicitacao = new SolicitacaoBeneficio();
            solicitacao.setCpf(cpf);
            solicitacao.setTempoContribuicaoCalculado(totalMeses);
            solicitacao.setValorMedioContribuicoes(mediaContribuicao);
            solicitacao.setValorConcedido(valorConcedido);
            solicitacao.setStatus(status);
            solicitacao.setMensagem(mensagem);
            solicitacao.setTotalBeneficios(valorConcedido);
            solicitacao.setTipoBeneficio(beneficio.getTipo());
            solicitacao.setAtivo(true);

            solicitacaoBeneficioRepository.save(solicitacao);

            // Retorna DTO com os dados relevantes
            return new RetornarSolicitacaoBeneficioDTO(
                    cpf,
                    totalMeses,
                    valorConcedido,
                    status,
                    mensagem,
                    valorConcedido,
                    beneficio.getTipo(),
                    true
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar contribuições: " + e.getMessage());
        }
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



// listar total de beneficios por cpf
public TotalBeneficiosPorCpfDTO calcularTotalDeBeneficiosPorCpf(String cpf) {
    List<SolicitacaoBeneficio> solicitacoes = solicitacaoBeneficioRepository.findByCpf(cpf);

    // Filtra apenas os benefícios concedidos (status = "ativo")
    BigDecimal total = solicitacoes.stream()
            .filter(s -> "ativo".equalsIgnoreCase(s.getStatus()))
            .map(SolicitacaoBeneficio::getValorConcedido)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Se teve pelo menos um benefício concedido
    String status = total.compareTo(BigDecimal.ZERO) > 0 ? "concedido" : "não concedido";

    return new TotalBeneficiosPorCpfDTO(cpf, status, total);
}





















}