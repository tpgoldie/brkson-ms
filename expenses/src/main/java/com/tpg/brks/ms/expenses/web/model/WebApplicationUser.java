package com.tpg.brks.ms.expenses.web.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class WebApplicationUser implements UserDetails {
    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private List<GrantedAuthority> authorities;

    public WebApplicationUser() {}

    @Override
    public String toString() {
        return username;
    }
}
