package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.ExpenseStatus;
import com.tpg.brks.ms.expenses.domain.ExpenseType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.ExpenseStatus.PENDING;
import static java.util.Collections.singletonList;

public interface ExpenseFixture {
    default ExpenseEntity anExpense(String description, Date expenseDate, Date dateEntered, ExpenseStatus status, ExpenseType expenseType,
                                    BigDecimal amount) {
        ExpenseEntity entity = new ExpenseEntity();

        entity.setExpenseDate(expenseDate);
        entity.setDateEntered(dateEntered);
        entity.setStatus(status);
        entity.setExpenseType(expenseType);
        entity.setAmount(amount);

        entity.setDescription(description);

        return entity;
    }

    default ExpenseEntity aPendingExpense(ExpenseReportEntity report, String description, Date expenseDate, Date dateEntered,
                                          ExpenseType expenseType, BigDecimal amount) {
        ExpenseEntity expense = anExpense(description, expenseDate, dateEntered, PENDING, expenseType, amount);
        expense.setExpenseReports(singletonList(report));

        expense.setAttachedFilename("filename_1.txt");
        return expense;
    }
}
