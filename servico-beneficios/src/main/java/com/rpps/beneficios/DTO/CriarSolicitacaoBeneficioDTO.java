package com.rpps.beneficios.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CriarSolicitacaoBeneficioDTO {
    private String cpf;
    private int beneficioId;
}
