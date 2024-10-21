package br.com.blood.bank.enums;

public enum EnumUserPermission {

    USER("USER", 2),
    ADMIN("ROLE_ADMIN", 1);

     final private String role;
     final private int code;

    EnumUserPermission(String role, int code) {
        this.role = role;
        this.code = code;
    }

    public String getRole() {
        return this.role;
    }

    public int getCode() {
        return this.code;
    }
}
