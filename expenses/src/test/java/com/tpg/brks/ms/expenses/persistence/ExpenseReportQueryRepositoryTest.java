package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportQueryRepository;
import com.tpg.brks.ms.utils.UniqueIdGeneration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class ExpenseReportQueryRepositoryTest extends RepositoryTest implements UniqueIdGeneration {

    @Test
    public void whenRequestingExpenseReportsByAssignment_anAssignment_thenAllExpenseReportsAreReturned() {
        AccountEntity account = givenAnAccount();

        AssignmentEntity assignment = givenAnAssignment(account);

        ExpenseEntity expense = givenAnExpense();

        ExpenseReportEntity expectedReport = givenAnExpenseReport(assignment, singletonList(expense));

        List<ExpenseReportEntity> actualReports = whenRequestingExpenseReportsByAssignment(assignment);

        thenActualReportMatchesExpectedReport(actualReports, expectedReport);
    }

    private void thenActualReportMatchesExpectedReport(List<ExpenseReportEntity> actualReports, ExpenseReportEntity expectedReport) {

        assertThat(actualReports, hasSize(1));

        ExpenseReportEntity actualReport = actualReports.get(0);

        assertThat(actualReport, hasProperty("id", is(expectedReport.getId())));

        assertThat(actualReport.getAssignment(), hasProperty("description",
                is(expectedReport.getAssignment().getDescription())));

        assertThat(actualReport.getExpenses(), hasSize(1));

        assertThat(actualReport.getExpenses().get(0),
                hasProperty("id", is(expectedReport.getExpenses().get(0).getId())));
    }

    private List<ExpenseReportEntity> whenRequestingExpenseReportsByAssignment(AssignmentEntity assignment) {
        return expenseReportQueryRepository.findByAssignmentId(assignment.getId());
    }
}
