package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.utils.DateGeneration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;
import static java.util.Collections.emptyList;

public interface PersistenceGiven extends DateGeneration, AccountFixture, AssignmentFixture, ExpenseReportFixture, ExpenseFixture {
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

    default ExpenseReportEntity givenAnExpenseReportEntity(AssignmentEntity assignmentEntity) {
        Date start = generateDate(3, 11, 2016);
        Date end = generateDate(2, 2, 2017);

        return anOpenExpenseReport(assignmentEntity, "Report 1", start, end);
    }

    default ExpenseEntity givenAnExpenseEntity(ExpenseReportEntity expenseReport) {
        Date expenseDate = generateDate(23, 6, 2016);
        Date dateEntered = generateDate(25, 6, 2016);

        return aPendingExpense(expenseReport, "Expense 1", expenseDate, dateEntered, SUBSISTENCE, new BigDecimal(125.45));
    }
}
