package com.tpg.brks.ms.expenses.web.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static java.util.Collections.singletonList;

public interface WebApplicationUserFixture {
    default WebApplicationUser johnDoeWebAppUser() {
        return aWebApplicationUser("jdoe", "abc123");
    }

    default WebApplicationUser aWebApplicationUser(String username, String password) {
        WebApplicationUser user = new WebApplicationUser();

        user.setUsername(username);
        user.setPassword(password);

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        user.setAuthorities(singletonList(new SimpleGrantedAuthority("ROLE_EXPENSE_USER")));
        return user;
    }
}
