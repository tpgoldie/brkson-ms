package com.tpg.brks.ms.expenses.domain;

import static com.tpg.brks.ms.expenses.domain.AccountStatus.OPEN;

public interface AccountFixture {
    default Account johnDoeAccount() {
        return anAccount("John", "Doe", "jdoe", OPEN);
    }

    default Account anAccount(String firstName, String lastName, String username, AccountStatus status) {
        return Account.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .status(status)
                .build();
    }
}
