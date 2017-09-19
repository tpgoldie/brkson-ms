package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentLifecycleRepository;
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
import static org.hamcrest.core.Is.is;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpenseReportQueryRepositoryTest implements UniqueIdGeneration, DateGeneration, AssignmentFixture,
        ExpenseReportFixture, ExpenseFixture {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AssignmentLifecycleRepository assignmentLifecycleRepository;

    @Autowired
    private ExpenseReportLifecycleRepository expenseReportLifecycleRepository;

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
        assertThat(actualReport, hasProperty("id", is(expectedReport.getAssignment().getDescription())));
        assertThat(actualReport.getAssignment().getDescription(), is(expectedReport.getAssignment().getDescription()));
    }

    private AssignmentEntity givenAnAssignment() {
        Date startDate = generateDate(21,4,2017);

        AssignmentEntity assignment = anOpenAssignment("assignment 1", startDate);

        return assignmentLifecycleRepository.save(assignment);
    }

    private ExpenseReportEntity givenAnExpenseReport(AssignmentEntity assignment, List<ExpenseEntity> expenses) {
        return anExpenseReport(assignment, expenses);
    }

    private ExpenseEntity givenAnExpense() {
        Date expenseDate = generateDate(10, 3, 2017);

        return anExpense("lunch", expenseDate);
    }

    private ExpenseReportEntity whenRequestingExpensesSummaryByAssignment(AssignmentEntity assignment) {
        return expenseReportRepository.findByAssignmentId(assignment.getId()).get();
    }
}
