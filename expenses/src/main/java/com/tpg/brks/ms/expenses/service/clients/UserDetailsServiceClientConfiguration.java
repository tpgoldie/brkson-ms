package com.tpg.brks.ms.expenses.service.clients;

public interface UserDetailsServiceClientConfiguration {
    String getRootUri();

    int getConnectionTimeout();

    int getReadTimeout();
}
