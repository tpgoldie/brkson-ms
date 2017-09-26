package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Account;

import java.util.Optional;

public interface AccountQueryService {
    Optional<Account> findAccountByUsername(String username);
}
