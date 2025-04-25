package com.rpps.beneficios.DTO;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

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
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formato.format(valorConcedido);
    }

    @JsonGetter("totalBeneficios")
    public String formatarTotalBeneficios() {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formato.format(totalBeneficios);
    }


}












