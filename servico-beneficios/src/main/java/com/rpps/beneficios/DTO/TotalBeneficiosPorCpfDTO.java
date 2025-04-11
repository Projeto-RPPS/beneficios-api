package com.rpps.beneficios.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalBeneficiosPorCpfDTO {

    private String cpf;
    private String status;
    private BigDecimal totalBeneficios;



}
