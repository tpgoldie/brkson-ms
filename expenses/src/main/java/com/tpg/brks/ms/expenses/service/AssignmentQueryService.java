package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;

import java.util.Optional;

public interface AssignmentQueryService {
    Optional<Assignment> findCurrentAssignmentForAccount(Account account);
}
