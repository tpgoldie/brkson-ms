package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;

public interface Given extends DateGeneration, AccountFixture, AssignmentFixture, ExpenseReportFixture, ExpenseFixture {
    Long ID = 101L;

    default AccountEntity givenAnAccount() {
        AccountEntity accountEntity = anOpenAccount("John", "Doe", "jdoe");
        accountEntity.setId(ID);
        return accountEntity;
    }

    default AssignmentEntity givenAnAssignment(AccountEntity accountEntity) {
        Date startDate = generateDate(1, 1, 2016);

        AssignmentEntity assignment = anOpenAssignment(accountEntity, "Assignment 1", startDate);
        assignment.setId(ID);

        return assignment;
    }

    default ExpenseReportEntity givenAnExpenseReport(AssignmentEntity assignmentEntity, List<ExpenseEntity> expenses) {
        ExpenseReportEntity expenseReportEntity = anExpenseReport(assignmentEntity, "Report 1", expenses);
        expenseReportEntity.setId(ID);

        return expenseReportEntity;
    }

    default ExpenseEntity givenAnExpense() {
        Date expenseDate = generateDate(23, 6, 2016);

        ExpenseEntity expenseEntity = aPendingExpense("Expense 1", expenseDate, SUBSISTENCE, new BigDecimal(125.45));
        expenseEntity.setId(ID);

        return expenseEntity;
    }
}
