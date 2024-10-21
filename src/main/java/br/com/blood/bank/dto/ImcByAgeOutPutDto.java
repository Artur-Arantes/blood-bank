package br.com.blood.bank.dto;

import java.math.BigDecimal;

public record ImcByAgeOutPutDto(
         int fromAge,
         int toAge,
         BigDecimal imc
) {
}
