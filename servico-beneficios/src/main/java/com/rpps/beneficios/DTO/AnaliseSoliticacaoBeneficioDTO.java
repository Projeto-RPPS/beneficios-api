package com.rpps.beneficios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnaliseSoliticacaoBeneficioDTO {
    private String cpf;
    private int tempoContribuicao;
    private boolean concedido;
    private double valorConcedido;
    private String mensagem;
}
