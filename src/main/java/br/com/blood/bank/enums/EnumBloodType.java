package br.com.blood.bank.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum EnumBloodType {

    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-");

    private final String description;

    EnumBloodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static EnumBloodType getByDescription(String description) {
        for (EnumBloodType bloodType : EnumBloodType.values()) {
            if (bloodType.getDescription().equalsIgnoreCase(description)) {
                return bloodType;
            }
        }
        throw new IllegalArgumentException("Tipo sanguíneo não encontrado: " + description);
    }

    public List<EnumBloodType> getPossibleDonors() {
        switch (this) {
            case O_NEGATIVE:
                return Arrays.asList(O_NEGATIVE);
            case O_POSITIVE:
                return Arrays.asList(O_NEGATIVE, O_POSITIVE);
            case A_NEGATIVE:
                return Arrays.asList(O_NEGATIVE, A_NEGATIVE);
            case A_POSITIVE:
                return Arrays.asList(O_NEGATIVE, O_POSITIVE, A_NEGATIVE, A_POSITIVE);
            case B_NEGATIVE:
                return Arrays.asList(O_NEGATIVE, B_NEGATIVE);
            case B_POSITIVE:
                return Arrays.asList(O_NEGATIVE, O_POSITIVE, B_NEGATIVE, B_POSITIVE);
            case AB_NEGATIVE:
                return Arrays.asList(O_NEGATIVE, A_NEGATIVE, B_NEGATIVE, AB_NEGATIVE);
            case AB_POSITIVE:
                return Arrays.asList(O_NEGATIVE, O_POSITIVE, A_NEGATIVE, A_POSITIVE, B_NEGATIVE, B_POSITIVE, AB_NEGATIVE, AB_POSITIVE);
            default:
                return Collections.emptyList();
        }
    }
}

