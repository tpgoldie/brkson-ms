package com.tpg.brks.ms.expenses.domain;

import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.math.BigDecimal;
import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.ExpenseStatus.PENDING;

public interface ExpenseFixture extends DateGeneration {
    default Expense aPendingExpense(Long id, String description, Date dateEntered, Date expenseDate, ExpenseType expenseType,
                                    BigDecimal amount) {
        Expense expense = new Expense();

        expense.setId(id);
        expense.setDescription(description);
        expense.setDateEntered(toDdMmYyyyFormat(dateEntered));
        expense.setExpenseDate(toDdMmYyyyFormat(expenseDate));
        expense.setStatus(PENDING);
        expense.setExpenseType(expenseType);
        expense.setAmount(amount);
        return expense;
    }
}
