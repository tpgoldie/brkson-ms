package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.service.DomainGiven;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AssignmentConverterTest implements PersistenceGiven, DomainGiven {
    private AssignmentConverter converter;

    @Before
    public void setUp() {
        converter = new AssignmentConverter(new AccountConverter());
    }

    @Test
    public void convertEntity() {
        AccountEntity accountEntity = givenAnAccountEntity();

        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);

        Assignment actual = whenConvertingEntityToDomain(assignmentEntity);

        thenPersistenceEntityIsConvertedToDomainEntity(actual, assignmentEntity);
    }

    private Assignment whenConvertingEntityToDomain(AssignmentEntity assignmentEntity) {
        return converter.convert(assignmentEntity);
    }

    private void thenPersistenceEntityIsConvertedToDomainEntity(Assignment actual, AssignmentEntity assignmentEntity) {
        assertThat(actual.getAccount(), hasProperty("id", is(assignmentEntity.getAccount().getId())));

        assertThat(actual.getAccount(), hasProperty("firstName",
            is(assignmentEntity.getAccount().getName().getFirstName())));

        assertThat(actual.getAccount(), hasProperty("lastName",
            is(assignmentEntity.getAccount().getName().getLastName())));

        assertThat(actual, hasProperty("description", is(assignmentEntity.getDescription())));
        assertThat(actual, hasProperty("startDate", is(assignmentEntity.getStartDate())));
        assertThat(actual, hasProperty("endDate", is(assignmentEntity.getEndDate())));
        assertThat(actual, hasProperty("status", is(assignmentEntity.getStatus())));
    }
}
