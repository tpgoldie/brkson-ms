package com.tpg.brks.ms.expenses.persistence.entities;

public interface AccountFixture {
    default AccountEntity anOpenAccount(String firstName, String lastName, String username) {
        return AccountEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .status("OPEN")
                .build();
    }
}
