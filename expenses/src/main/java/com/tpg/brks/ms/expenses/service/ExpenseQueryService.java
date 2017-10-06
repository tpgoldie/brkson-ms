package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Expense;

import java.util.Optional;

public interface ExpenseQueryService {
    Optional<Expense> getExpense(Long id);
}
