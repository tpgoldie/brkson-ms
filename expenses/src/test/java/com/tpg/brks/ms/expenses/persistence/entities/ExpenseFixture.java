package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.ExpenseStatus;
import com.tpg.brks.ms.expenses.domain.ExpenseType;

import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.ExpenseStatus.PENDING;

public interface ExpenseFixture {
    default ExpenseEntity anExpense(String description, Date expenseDate, ExpenseStatus status, ExpenseType expenseType) {
        ExpenseEntity entity = ExpenseEntity.builder()
            .dateEntered(new Date())
            .expenseDate(expenseDate)
                .status(status)
                .expenseType(expenseType)
                .build();

            entity.setDescription(description);
            return entity;
    }

    default ExpenseEntity aPendingExpense(String description, Date expenseDate, ExpenseType expenseType) {
        return anExpense(description, expenseDate, PENDING, expenseType);
    }
}
