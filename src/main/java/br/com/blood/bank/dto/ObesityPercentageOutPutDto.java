package br.com.blood.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ObesityPercentageOutPutDto(
        @JsonProperty("male_percentage_of_obesity")
        BigDecimal malePercentageOfObesity,
        @JsonProperty("female_percentage_of_obesity")
        BigDecimal femalePercentageOfObesity
) {
}
