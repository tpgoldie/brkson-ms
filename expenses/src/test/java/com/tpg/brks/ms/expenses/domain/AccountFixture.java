package com.tpg.brks.ms.expenses.domain;

import static com.tpg.brks.ms.expenses.domain.AccountStatus.OPEN;

public interface AccountFixture {
    Long ID = 101L;

    default Account johnDoeAccount() {
        return anAccount(ID,"John", "Doe", "jdoe", OPEN);
    }

    default Account anAccount(Long id, String firstName, String lastName, String username, AccountStatus status) {
        Account account = Account.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .status(status)
                .build();

        account.setId(id);

        return account;
    }
}
