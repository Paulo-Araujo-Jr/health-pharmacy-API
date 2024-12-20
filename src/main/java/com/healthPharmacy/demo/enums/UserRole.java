package com.healthPharmacy.demo.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    CUSTOMER("ROLE_CUSTOMER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
