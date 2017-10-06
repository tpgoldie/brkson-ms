package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.persistence.PeristenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.service.DomainGiven;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AccountConverterTest implements PeristenceGiven, DomainGiven {
    private AccountConverter converter;

    @Before
    public void setUp() {
        converter = new AccountConverter();
    }

    @Test
    public void convertEntity() {
        AccountEntity accountEntity = givenAnAccountEntity();

        Account actual = whenConvertingEntityToDomain(accountEntity);

        thenPersistenceEntityIsConvertedToDomainEntity(actual, accountEntity);
    }

    private Account whenConvertingEntityToDomain(AccountEntity accountEntity) {
        return converter.convert(accountEntity);
    }

    private void thenPersistenceEntityIsConvertedToDomainEntity(Account actual, AccountEntity accountEntity) {
        assertThat(actual, hasProperty("id", is(accountEntity.getId())));
        assertThat(actual, hasProperty("firstName", is(accountEntity.getName().getFirstName())));
        assertThat(actual, hasProperty("lastName", is(accountEntity.getName().getLastName())));
        assertThat(actual, hasProperty("status", is(accountEntity.getStatus())));
    }
}
