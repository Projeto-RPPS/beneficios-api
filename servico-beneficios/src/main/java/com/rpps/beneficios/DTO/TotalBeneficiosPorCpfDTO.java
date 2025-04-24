package com.rpps.beneficios.DTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalBeneficiosPorCpfDTO {

    private String cpf;
    private String status;
    private BigDecimal totalBeneficios;

    @JsonGetter("totalBeneficios")
    public String getTotalFormatado() {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formato.format(totalBeneficios);
    }
}
