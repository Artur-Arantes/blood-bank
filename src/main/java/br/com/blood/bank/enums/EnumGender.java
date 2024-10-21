package br.com.blood.bank.enums;

public enum EnumGender {

    MALE("Masculino"),
    FEMALE("Feminino");

    private final String description;

    EnumGender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
