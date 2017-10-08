package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import com.tpg.brks.ms.expenses.service.converters.AccountConverter;
import com.tpg.brks.ms.expenses.service.converters.AssignmentConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AssignmentQueryServiceImplTest extends ServiceImplTest {
    @Mock
    private AssignmentQueryRepository assignmentQueryRepository;

    private AssignmentQueryServiceImpl assignmentQueryService;

    @Before
    public void setUp() {
        assignmentQueryService = new AssignmentQueryServiceImpl(assignmentQueryRepository,
            new AssignmentConverter(new AccountConverter()));
    }

    @Test
    public void findingAssignmentsByAccount() {
        AccountEntity accountEntity = givenAnAccountEntity();

        AssignmentEntity assignmentEntity = givenAnAssignmentEntity(accountEntity);

        Account account = givenAnAccount();

        Assignment assignment = whenFindingCurrentAssignmentForAccount(account, assignmentEntity);

        thenCurrentAssignmentReturned(account.getId(), assignmentEntity, assignment);
    }

    private Assignment whenFindingCurrentAssignmentForAccount(Account account, AssignmentEntity assignmentEntity) {

        when(assignmentQueryRepository.findFirstByAccountIdOrderByStartDateDesc(account.getId())).thenReturn(Optional.of(assignmentEntity));

        return assignmentQueryService.findCurrentAssignmentForAccount(account).get();
    }

    private void thenCurrentAssignmentReturned(Long accountId, AssignmentEntity assignmentEntity, Assignment assignment) {

        assertThat(assignment.getAccount(), hasProperty("id", is(assignmentEntity.getAccount().getId())));
        assertThat(assignment, hasProperty("id", is(assignmentEntity.getId())));

        verify(assignmentQueryRepository).findFirstByAccountIdOrderByStartDateDesc(accountId);
    }
}
