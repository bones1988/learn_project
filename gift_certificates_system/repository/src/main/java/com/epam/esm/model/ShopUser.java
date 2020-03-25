package com.epam.esm.model;

import com.epam.esm.model.role.Role;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopUser extends AbstractModel {
    private String login;
    private Role role;
    private String password;
    private boolean active;
    private List<Purchase> purchases;


    public ShopUser() {
        purchases = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShopUser)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ShopUser shopUser = (ShopUser) o;
        return active == shopUser.active &&
                Objects.equals(login, shopUser.login) &&
                role == shopUser.role &&
                Objects.equals(password, shopUser.password) &&
                Objects.equals(purchases, shopUser.purchases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, role, password, active, purchases);
    }
}
