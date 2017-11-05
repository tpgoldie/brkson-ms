package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.service.DomainGiven;
import com.tpg.brks.ms.expenses.utils.DateFormatting;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class ExpenseConverterTest implements PersistenceGiven, DomainGiven, DateFormatting {

    private ExpenseConverter converter;

    @Before
    public void setUp() {
        converter = new ExpenseConverter();
    }

    @Test
    public void convertEntity() {
        AccountEntity accountEntity = givenAnAccountEntity();
        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);
        ExpenseReportEntity expenseReportEntity = givenAnExpenseReportEntity(assignmentEntity);
        ExpenseEntity expenseEntity = givenAnExpenseEntity(expenseReportEntity);

        Expense actual = converter.convert(expenseEntity);

        assertThat(actual, hasProperty("amount", is(expenseEntity.getAmount())));
        assertThat(actual, hasProperty("description", is(expenseEntity.getDescription())));
        assertThat(actual, hasProperty("expenseDate", is(toDdMmYyyyFormat(expenseEntity.getExpenseDate()))));
        assertThat(actual, hasProperty("dateEntered", is(toDdMmYyyyFormat(expenseEntity.getDateEntered()))));
        assertThat(actual, hasProperty("status", is(expenseEntity.getStatus())));
        assertThat(actual, hasProperty("expenseType", is(expenseEntity.getExpenseType())));
        assertThat(actual, hasProperty("attachedFilename", is(expenseEntity.getAttachedFilename())));
    }
}
