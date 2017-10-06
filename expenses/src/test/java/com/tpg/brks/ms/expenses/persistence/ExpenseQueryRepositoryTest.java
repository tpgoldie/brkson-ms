package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExpenseQueryRepositoryTest extends RepositoryTest {
    @Test
    public void whenRequestingExpenseReportsByReportId_anAssignment_thenAllExpenseReportsAreReturned() {
        AccountEntity account = givenAnAccount();

        AssignmentEntity assignment = givenAnAssignment(account);

        ExpenseEntity expense = givenAnExpense();

        ExpenseReportEntity expectedReport = givenAnExpenseReport(assignment, singletonList(expense));

        ExpenseEntity expectedExpense = expectedReport.getExpenses().get(0);

        ExpenseEntity actualExpense = whenFindingExpenseById(expectedExpense.getId());

        thenActualExpenseMatchesExpectedExpense(actualExpense, expectedExpense);
    }

    private ExpenseEntity whenFindingExpenseById(Long id) {
        return expenseQueryRepository.findById(id).get();
    }

    private void thenActualExpenseMatchesExpectedExpense(ExpenseEntity actualExpense, ExpenseEntity expectedExpense) {

        assertThat(actualExpense, is(expectedExpense));
    }
}
