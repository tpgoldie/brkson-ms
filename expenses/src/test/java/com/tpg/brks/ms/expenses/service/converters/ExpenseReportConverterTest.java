package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.service.DomainGiven;
import com.tpg.brks.ms.expenses.utils.DateFormatting;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class ExpenseReportConverterTest implements PersistenceGiven, DomainGiven, DateFormatting {
    private ExpenseReportConverter converter;

    @Before
    public void setUp() {
        converter = new ExpenseReportConverter();
    }

    @Test
    public void convertEntity() {
        AccountEntity accountEntity = givenAnAccountEntity();
        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);
        ExpenseReportEntity expenseReportEntity = givenAnExpenseReportEntity(assignmentEntity);
        ExpenseEntity expenseEntity = givenAnExpenseEntity(expenseReportEntity);
        expenseReportEntity.setExpenses(singletonList(expenseEntity));
        ExpenseReport actual = converter.convert(expenseReportEntity);

        assertThat(actual.getAssignment(), hasProperty("description", is(assignmentEntity.getDescription())));
        assertThat(actual, hasProperty("description", is(expenseReportEntity.getDescription())));
        assertThat(actual, hasProperty("periodStart", is(toDdMmYyyyFormat(expenseReportEntity.getPeriod().getStartDate()))));
        assertThat(actual, hasProperty("periodEnd", is(toDdMmYyyyFormat(expenseReportEntity.getPeriod().getEndDate()))));
        assertThat(actual, hasProperty("status", is(expenseReportEntity.getStatus())));

        ExpenseConverter expenseConverter = new ExpenseConverter();
        List<Expense> expenses = expenseReportEntity.getExpenses().stream().map(expenseConverter::convert).collect(toList());

        assertThat(actual, hasProperty("expenses", is(expenses)));
    }
}
