package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportQueryRepository;
import com.tpg.brks.ms.expenses.utils.DateFormatting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.tpg.brks.ms.expenses.service.GettingExpenseReportExpectation.then;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseReportQueryServiceImplTest implements PersistenceGiven, DateFormatting {

    @Mock
    private ExpenseReportQueryRepository expenseReportQueryRepository;

    @InjectMocks
    private ExpenseReportQueryServiceImpl expenseReportQueryService;

    @Test
    public void findingExpenseReportsByAssignment() {
        AccountEntity accountEntity = givenAnAccountEntity();

        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);

        ExpenseReportEntity expenseReportEntity = givenAnExpenseReportEntity(assignmentEntity);

        ExpenseEntity expenseEntity = givenAnExpenseEntity(expenseReportEntity);

        expenseReportEntity.setExpenses(singletonList(expenseEntity));

        List<ExpenseReport> actual = whenRequestingAnExpenseReportByAssignment(assignmentEntity, expenseReportEntity);

        thenExpenseReportsForGivenAssignmentAreFound(actual, expenseReportEntity);
    }

    @Test
    public void gettingAnExpenseReport() {
        AccountEntity accountEntity = givenAnAccountEntity();

        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);

        ExpenseReportEntity expenseReportEntity = givenAnExpenseReportEntity(assignmentEntity);

        ExpenseEntity expenseEntity = givenAnExpenseEntity(expenseReportEntity);

        expenseReportEntity.setExpenses(singletonList(expenseEntity));

        ExpenseReport actual = whenFindingAnExpenseReport(expenseReportEntity);

        then()
            .expectExpenseReportQueryRepository(expenseReportQueryRepository)
                .toFindExpenseReportById(expenseEntity.getId());
//        .and()
//            .assertExpenseReport(actual);

        assertThat(actual.getAssignment().getDescription(), is(assignmentEntity.getDescription()));
    }

    private ExpenseReport whenFindingAnExpenseReport(ExpenseReportEntity entity) {
        when(expenseReportQueryRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        return expenseReportQueryService.getExpenseReport(entity.getId()).get();
    }

    private List<ExpenseReport> whenRequestingAnExpenseReportByAssignment(AssignmentEntity assignment, ExpenseReportEntity expenseReport) {
        when(expenseReportQueryRepository.findByAssignmentId(assignment.getId())).thenReturn(singletonList(expenseReport));

        return expenseReportQueryService.getExpenseReportsForAssignment(assignment.getId());
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
}
