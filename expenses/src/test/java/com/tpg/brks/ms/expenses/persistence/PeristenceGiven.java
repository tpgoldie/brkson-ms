package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;

public interface PeristenceGiven extends DateGeneration, AccountFixture, AssignmentFixture, ExpenseReportFixture, ExpenseFixture {
    Long ID = 101L;

    default AccountEntity givenAnAccountEntity() {
        AccountEntity accountEntity = anOpenAccount("John", "Doe", "jdoe");
        accountEntity.setId(ID);
        return accountEntity;
    }

    default AssignmentEntity givenAnAssignmentEntity(AccountEntity accountEntity) {
        Date startDate = generateDate(1, 1, 2016);

        AssignmentEntity assignment = anOpenAssignment(accountEntity, "Assignment 1", startDate);
        assignment.setId(ID);

        return assignment;
    }

    default ExpenseReportEntity givenAnExpenseReportEntity(AssignmentEntity assignmentEntity, List<ExpenseEntity> expenses) {
        ExpenseReportEntity expenseReportEntity = anExpenseReport(assignmentEntity, "Report 1", expenses);
        expenseReportEntity.setId(ID);

        return expenseReportEntity;
    }

    default ExpenseEntity givenAnExpenseEntity() {
        Date expenseDate = generateDate(23, 6, 2016);

        ExpenseEntity expenseEntity = aPendingExpense("Expense 1", expenseDate, SUBSISTENCE, new BigDecimal(125.45));
        expenseEntity.setId(ID);

        return expenseEntity;
    }
}
