package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.AccountFixture;

public interface DomainGiven extends AccountFixture {
    default Account givenAnAccount() {
        return johnDoeAccount();
    }
}
