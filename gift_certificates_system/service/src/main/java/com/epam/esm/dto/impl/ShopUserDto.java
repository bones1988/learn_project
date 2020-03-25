package com.epam.esm.dto.impl;

import com.epam.esm.dto.AbstractDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * class for dat transfering user
 */
public class ShopUserDto extends AbstractDto {
    private String login;
    private String role;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean active;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShopUserDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ShopUserDto that = (ShopUserDto) o;
        return active == that.active &&
                Objects.equals(login, that.login) &&
                Objects.equals(role, that.role) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, role, password, active);
    }
}
