package com.tpg.brks.ms.expenses.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.QAssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    @Test
    public void findCurrentAssignmentForGivenAccount() {
        AccountEntity account = givenAnAccount();

        AssignmentEntity previousAssignment = givenPreviousAssignment(account);
        AssignmentEntity currentAssignment = givenCurrentAssignment(account);

        AssignmentEntity actual = whenFindingCurrentAssignmentByAccount(account, asList(previousAssignment, currentAssignment));

        thenActualAssignmentMatchesExpectedAssignment(actual, currentAssignment);
    }

    private AssignmentEntity whenFindingById(AssignmentEntity assignment) {
        return assignmentQueryRepository.findById(assignment.getId()).get();
    }

    private AssignmentEntity whenFindingCurrentAssignmentByAccount(AccountEntity account, List<AssignmentEntity> assignments) {
        BooleanExpression predicate = QAssignmentEntity.assignmentEntity.account.id.eq(account.getId());
        Iterable<AssignmentEntity> found = assignmentQueryRepository.findAll(predicate);

        assertThat(found, containsInAnyOrder(assignments.get(0), assignments.get(1)));

        return assignmentQueryRepository.findFirstByAccountIdOrderByStartDateDesc(account.getId()).get();
    }

    private void thenActualAssignmentMatchesExpectedAssignment(AssignmentEntity actual, AssignmentEntity expected) {
        assertThat(actual, hasProperty("id", is(expected.getId())));
        assertThat(actual, hasProperty("description", is(expected.getDescription())));
        assertThat(actual, hasProperty("startDate", is(expected.getStartDate())));
        assertThat(actual, hasProperty("endDate", is(expected.getEndDate())));
    }
}
