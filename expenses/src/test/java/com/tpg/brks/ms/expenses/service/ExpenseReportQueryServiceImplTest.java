package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportQueryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseReportQueryServiceImplTest implements PersistenceGiven {

    @Mock
    private ExpenseReportQueryRepository expenseReportQueryRepository;

    @InjectMocks
    private ExpenseReportQueryServiceImpl expenseReportQueryService;

    @Test
    public void findingExpenseReportsByAssignment() {
        AccountEntity accountEntity = givenAnAccountEntity();

        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);

        ExpenseEntity expenseEntity = givenAnExpenseEntity();

        ExpenseReportEntity expenseReportEntity = givenAnExpenseReportEntity(assignmentEntity, singletonList(expenseEntity));

        List<ExpenseReport> actual = whenRequestingAnExpenseReportByAssignment(assignmentEntity, expenseReportEntity);

        thenExpenseReportsForGivenAssignmentAreFound(actual, expenseReportEntity);
    }

    private void thenExpenseReportsForGivenAssignmentAreFound(List<ExpenseReport> expenseReports, ExpenseReportEntity expenseReportEntity) {
        assertThat(expenseReports, hasSize(1));

        ExpenseReport actual = expenseReports.get(0);

        assertThat(actual, hasProperty("id", is(expenseReportEntity.getId())));
        assertThat(actual, hasProperty("description", is(expenseReportEntity.getDescription())));
        assertThat(actual, hasProperty("status", is(expenseReportEntity.getStatus())));
        assertThat(actual, hasProperty("periodStart",
            is(toDdMmYyyyFormat(expenseReportEntity.getPeriod().getStartDate()))));
        assertThat(actual, hasProperty("periodEnd",
            is(toDdMmYyyyFormat(expenseReportEntity.getPeriod().getEndDate()))));
    }

    private List<ExpenseReport> whenRequestingAnExpenseReportByAssignment(AssignmentEntity assignment, ExpenseReportEntity expenseReport) {
        when(expenseReportQueryRepository.findByAssignmentId(assignment.getId())).thenReturn(singletonList(expenseReport));

        return expenseReportQueryService.getExpenseReportsForAssignment(assignment.getId());
    }
}
