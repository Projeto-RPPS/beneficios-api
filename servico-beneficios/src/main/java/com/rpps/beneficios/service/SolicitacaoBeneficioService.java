package com.rpps.beneficios.service;

import com.rpps.beneficios.DTO.*;
import com.rpps.beneficios.model.Beneficio;
import com.rpps.beneficios.model.SolicitacaoBeneficio;
import com.rpps.beneficios.repository.SolicitacaoBeneficioRepository;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.rpps.beneficios.repository.BeneficioRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;



import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


import java.util.List;

@Service



public class SolicitacaoBeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private SolicitacaoBeneficioRepository solicitacaoBeneficioRepository;


    // Utilizando RestTemplate para fazer a requisição HTTP (de outras APIs)
    @Autowired
    private RestTemplate restTemplate;

    @Value("${contribuicoes.api.endpoint}")
    private String contribuicoesApiEndpoint;



    // cria solicitação
    public RetornarSolicitacaoBeneficioDTO criarSolicitacao(CriarSolicitacaoBeneficioDTO dto) {
        Beneficio beneficio = obterBeneficioPorId(dto.getBeneficioId());
        List<ContribuicaoDTO> contribuicoes = consultarContribuicoesPorCpf(dto.getCpf());

//        if (contribuicoes.isEmpty()) {
//            throw new ContribuicaoNotFoundException("Nenhuma contribuição encontrada para o CPF informado.");
//        }

        int totalMeses = contribuicoes.size();
        BigDecimal media = calcularMediaContribuicoes(contribuicoes);
        boolean concedido = totalMeses >= beneficio.getTempoMinimoMeses();

        String status = concedido ? "ativo" : "inativo";
        String mensagem = concedido ? "Benefício concedido com sucesso" : "Contribuição insuficiente";
        BigDecimal valorConcedido = concedido
                ? calcularValorConcedido(media, beneficio.getPercentualBaseMedioContribuicoes())
                : BigDecimal.ZERO;

        SolicitacaoBeneficio solicitacao = montarSolicitacao(dto.getCpf(), totalMeses, media, valorConcedido, status, mensagem, beneficio.getTipo());
        solicitacaoBeneficioRepository.save(solicitacao);

        return new RetornarSolicitacaoBeneficioDTO(
                dto.getCpf(),
                totalMeses,
                valorConcedido,
                status,
                mensagem,
                valorConcedido,
                beneficio.getTipo(),
                true
        );



    }


    private Beneficio obterBeneficioPorId(int id){
        return beneficioRepository.findBeneficioByIdBeneficioAndAtivoIsTrue(id)
//                .orElseThrow(() -> new BeneficioNotFoundException("Benefício não encontrado para o ID: " + id));
               .orElseThrow(() -> new RuntimeException("Beneficio não encontrado"));
    }

    private List<ContribuicaoDTO> consultarContribuicoesPorCpf(String cpf) {
        try {
            String url = contribuicoesApiEndpoint + URLEncoder.encode(cpf, StandardCharsets.UTF_8);
            ResponseEntity<ContribuicaoDTO[]> response = restTemplate.getForEntity(url, ContribuicaoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
//            throw new ContribuicaoNotFoundException("Erro ao consultar contribuições: " + e.getMessage());
            throw new RuntimeException("Erro ao consultar contribuições: " + e.getMessage());
        }
    }

    private BigDecimal calcularMediaContribuicoes(List<ContribuicaoDTO> lista) {
        if (lista.isEmpty()) return BigDecimal.ZERO;
        BigDecimal soma = lista.stream()
                .map(ContribuicaoDTO::getValorContribuicao)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma.divide(BigDecimal.valueOf(lista.size()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularValorConcedido(BigDecimal media, int percentual) {
        return media.multiply(BigDecimal.valueOf(percentual).divide(BigDecimal.valueOf(100)));
    }

    private SolicitacaoBeneficio montarSolicitacao(String cpf, int meses, BigDecimal media, BigDecimal valor, String status, String mensagem, String tipo) {
        SolicitacaoBeneficio s = new SolicitacaoBeneficio();
        s.setCpf(cpf);
        s.setTempoContribuicaoCalculado(meses);
        s.setValorMedioContribuicoes(media);
        s.setValorConcedido(valor);
        s.setStatus(status);
        s.setMensagem(mensagem);
        s.setTotalBeneficios(valor);
        s.setTipoBeneficio(tipo);
        s.setAtivo(true);
        return s;
    }





    // lista todas as solicitações (ativas e desativadas)
    public List<SolicitacaoBeneficio> listarTodas() {
        return solicitacaoBeneficioRepository.findAll();
    }


    //Lista apenas as solcitações ativas
    public List<SolicitacaoBeneficio> listarAtivas() {
        return solicitacaoBeneficioRepository.findByAtivoTrue();
    }



    public boolean desativarSolicitacao(int id) {
        return solicitacaoBeneficioRepository.findById(id).map(solicitacao -> {
            solicitacao.setAtivo(false);
            solicitacao.setStatus("inativo");
            solicitacao.setMensagem("Solicitação de benefício desativada");
            solicitacaoBeneficioRepository.save(solicitacao);
            return true;
        }).orElse(false);
    }



// listar total de beneficios por cpf
public TotalBeneficiosPorCpfDTO calcularTotalDeBeneficiosPorCpf(String cpf) {
    List<SolicitacaoBeneficio> solicitacoes = solicitacaoBeneficioRepository.findSolicitacaoBeneficioByAtivoAndCpf(true,cpf);


    if (solicitacoes.isEmpty()) {
        return new TotalBeneficiosPorCpfDTO(cpf, "solicitação desativada", BigDecimal.ZERO);
    }

    // Filtra apenas os benefícios concedidos
    BigDecimal total = solicitacoes.stream()
            .filter(s -> "ativo".equalsIgnoreCase(s.getStatus()))
            .map(SolicitacaoBeneficio::getValorConcedido)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Se teve pelo menos um benefício concedido,
    String status = total.compareTo(BigDecimal.ZERO) > 0 ? "concedido" : "não concedido";

    return new TotalBeneficiosPorCpfDTO(cpf, status, total);
}





















}