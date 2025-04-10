package com.rpps.beneficios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficioDTO {
    private int idBeneficio;
    private String tipo;
    private String descricao;
    private int tempoMinimoMeses;
    private int percentualBaseMedioContribuicoes;
    private String mensagem;
    private boolean ativo = true;  // Controle de desativação lógica
}

