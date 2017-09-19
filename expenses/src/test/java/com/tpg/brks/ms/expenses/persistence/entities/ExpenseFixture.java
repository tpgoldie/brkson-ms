package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Date;

public interface ExpenseFixture {
    default ExpenseEntity anExpense(String description, Date expenseDate) {
        ExpenseEntity entity = ExpenseEntity.builder()
            .dateEntered(new Date())
            .expenseDate(expenseDate)
                .build();

            entity.setDescription(description);
            return entity;
    }
}
