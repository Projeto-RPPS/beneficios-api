package com.rpps.beneficios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ContribuicaoDTO {
    private int idContribuicao;
    private int idContribuinte;
    private LocalDate dataContribuicao;
    private BigDecimal valorContribuicao;
    private LocalDate dataReferencia;
    private int idSalarioMinimo;
}


// contribuiçãoDTO do pedro

//Long idContribuicao,
//@NotNull(message = "Id contribuinte não pode ser null")
//Long idContribuinte,
//LocalDate dataContribuicao,
//BigDecimal valorContribuicao,
//@NotNull(message = "A data referente a contribuição é obrigatória")
//LocalDate dataReferencia,
//Long idSalarioMinimo
//                              )