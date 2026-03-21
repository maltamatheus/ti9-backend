package com.ti9.ti9_backend.security.enums;

public enum EnumUserRole {
    ADMIN("admin"),
    AUDITOR("auditor"),
    VIEWER("viewer");

    private String role;

    EnumUserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
