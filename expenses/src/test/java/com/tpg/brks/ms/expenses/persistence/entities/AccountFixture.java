package com.tpg.brks.ms.expenses.persistence.entities;

import static com.tpg.brks.ms.expenses.domain.AccountStatus.OPEN;

public interface AccountFixture {
    default AccountEntity anOpenAccount(String firstName, String lastName, String username) {
        Name name = new Name();
        name.setFirstName(firstName);
        name.setLastName(lastName);

        return AccountEntity.builder()
            .name(name)
            .username(username)
            .status(OPEN)
            .build();
    }
}
