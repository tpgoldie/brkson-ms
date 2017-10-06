package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class AssignmentQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private AssignmentQueryRepository assignmentQueryRepository;

    @Test
    public void findById_anId_findsAnExistingAssignmentMatchingTheId() {
        AccountEntity account = givenAnAccount();

        AssignmentEntity assignment = givenAnAssignment(account);

        AssignmentEntity actual = whenFindingById(assignment);

        thenActualAssignmentMatchesExpectedAssignment(actual, assignment);
    }

    private AssignmentEntity whenFindingById(AssignmentEntity assignment) {
        return assignmentQueryRepository.findById(assignment.getId()).get();
    }

    private void thenActualAssignmentMatchesExpectedAssignment(AssignmentEntity actual, AssignmentEntity expected) {
        assertThat(actual, hasProperty("id", is(expected.getId())));
        assertThat(actual, hasProperty("description", is(expected.getDescription())));
        assertThat(actual, hasProperty("startDate", is(expected.getStartDate())));
        assertThat(actual, hasProperty("endDate", is(expected.getEndDate())));
    }
}
