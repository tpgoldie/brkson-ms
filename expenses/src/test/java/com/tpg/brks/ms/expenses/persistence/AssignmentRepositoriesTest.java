package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.ExpensesMsApplication;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentFixture;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import com.tpg.brks.ms.utils.DateGeneration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {ExpensesMsApplication.class})
public class AssignmentRepositoriesTest extends RepositoryTest implements DateGeneration, AssignmentFixture {

    @Autowired
    private AssignmentLifecycleRepository assignmentLifecycleRepository;

    @Autowired
    private AssignmentQueryRepository assignmentQueryRepository;

    private AssignmentEntity givenAnAssignment() {
        Date startDate = generateDate(21,4,2017);

        AssignmentEntity assignment = anOpenAssignment("assignment 1", startDate);

        return assignmentLifecycleRepository.save(assignment);
    }

    @Test
    public void findById_anId_findsAnExistingAssignmentMatchingTheId() {
        AssignmentEntity assignment = givenAnAssignment();

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
