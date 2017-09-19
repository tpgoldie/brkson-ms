package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportQueryRepository;
import com.tpg.brks.ms.utils.DateGeneration;
import com.tpg.brks.ms.utils.UniqueIdGeneration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpenseReportRepositoriesTest extends RepositoryTest implements UniqueIdGeneration, DateGeneration, AssignmentFixture,
        ExpenseReportFixture, ExpenseFixture {

    @Autowired
    private AssignmentLifecycleRepository assignmentLifecycleRepository;

    @Autowired
    private ExpenseReportLifecycleRepository expenseReportLifecycleRepository;

    @Autowired
    private ExpenseLifecycleRepository expenseLifecycleRepository;

    @Autowired
    private ExpenseReportQueryRepository expenseReportRepository;

    @Test
    public void whenRequestingExpensesSummaryByAssignment_anAssignment_thenAllExpenseReportsAreReturned() {
        AssignmentEntity assignment = givenAnAssignment();

        ExpenseEntity expense = givenAnExpense();

        ExpenseReportEntity expectedReport = givenAnExpenseReport(assignment, singletonList(expense));

        ExpenseReportEntity actualReport = whenRequestingExpensesSummaryByAssignment(assignment);

        thenActualReportMatchesExpectedReport(actualReport, expectedReport);
    }

    private void thenActualReportMatchesExpectedReport(ExpenseReportEntity actualReport, ExpenseReportEntity expectedReport) {

        assertThat(actualReport, hasProperty("id", is(expectedReport.getId())));

        assertThat(actualReport.getAssignment(), hasProperty("description",
                is(expectedReport.getAssignment().getDescription())));

        assertThat(actualReport.getExpenses(), hasSize(1));
        assertThat(actualReport.getExpenses().get(0),
                hasProperty("id", is(expectedReport.getExpenses().get(0).getId())));
    }

    private AssignmentEntity givenAnAssignment() {

        Date startDate = generateDate(21,4,2017);

        AssignmentEntity assignment = anOpenAssignment("assignment 1", startDate);

        return assignmentLifecycleRepository.save(assignment);
    }

    private ExpenseReportEntity givenAnExpenseReport(AssignmentEntity assignment, List<ExpenseEntity> expenses) {
        ExpenseReportEntity report = anExpenseReport(assignment, "report 1", expenses);

        return expenseReportLifecycleRepository.save(report);
    }

    private ExpenseEntity givenAnExpense() {
        Date expenseDate = generateDate(10, 3, 2017);

        ExpenseEntity expense = anExpense("lunch", expenseDate, "PENDING", "SUBSISTENCE");

        return expenseLifecycleRepository.save(expense);
    }

    private ExpenseReportEntity whenRequestingExpensesSummaryByAssignment(AssignmentEntity assignment) {
        return expenseReportRepository.findByAssignmentId(assignment.getId()).get();
    }
}
