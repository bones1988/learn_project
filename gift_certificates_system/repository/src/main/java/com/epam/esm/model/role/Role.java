package com.epam.esm.model.role;

public enum Role {
    ADMINISTRATOR("ADMINISTRATOR"),
    USER("USER");
    
    private String role; //NOSONAR

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
