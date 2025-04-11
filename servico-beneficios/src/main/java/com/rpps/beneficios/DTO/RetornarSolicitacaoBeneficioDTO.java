package com.rpps.beneficios.DTO;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RetornarSolicitacaoBeneficioDTO {

    private String cpf;
    private int tempoContribuicaoCalculado;
    private BigDecimal valorConcedido;
    private String status;
    private String mensagem;
    private BigDecimal totalBeneficios;
    private String tipoBeneficio;
    private boolean ativo = true;


    @JsonGetter("valorConcedido")
    public String formatarValorConcedido() {
        return valorConcedido.setScale(2, RoundingMode.HALF_UP).toString();
    }

    @JsonGetter("totalBeneficios")
    public String formatarTotalBeneficios() {
        return totalBeneficios.setScale(2, RoundingMode.HALF_UP).toString();
    }

}












