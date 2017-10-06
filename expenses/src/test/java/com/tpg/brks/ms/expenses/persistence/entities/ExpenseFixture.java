package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.ExpenseStatus;
import com.tpg.brks.ms.expenses.domain.ExpenseType;

import java.math.BigDecimal;
import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.ExpenseStatus.PENDING;

public interface ExpenseFixture {
    default ExpenseEntity anExpense(String description, Date expenseDate, ExpenseStatus status, ExpenseType expenseType,
                                    BigDecimal amount) {
        ExpenseEntity entity = ExpenseEntity.builder()
            .dateEntered(new Date())
            .expenseDate(expenseDate)
                .status(status)
                .expenseType(expenseType)
                .amount(amount)
                .build();

            entity.setDescription(description);
            return entity;
    }

    default ExpenseEntity aPendingExpense(String description, Date expenseDate, ExpenseType expenseType, BigDecimal amount) {
        return anExpense(description, expenseDate, PENDING, expenseType, amount);
    }
}
