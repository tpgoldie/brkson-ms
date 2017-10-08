package com.tpg.brks.ms.expenses.persistence.entities;

import static com.tpg.brks.ms.expenses.domain.AccountStatus.OPEN;

public interface AccountFixture {
    default AccountEntity anOpenAccount(String firstName, String lastName, String username) {
        Name name = new Name();
        name.setFirstName(firstName);
        name.setLastName(lastName);

        AccountEntity entity = new AccountEntity();
        entity.setName(name);
        entity.setUsername(username);
        entity.setStatus(OPEN);

        return entity;
    }
}
