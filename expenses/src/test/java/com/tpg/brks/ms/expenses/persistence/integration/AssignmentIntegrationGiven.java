package com.tpg.brks.ms.expenses.persistence.integration;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentFixture;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import java.util.Date;

@Value
public class AssignmentIntegrationGiven implements DateGeneration, PersistenceGiven, AssignmentFixture {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AssignmentQueryRepository assignmentQueryRepository;

    @Autowired
    private AssignmentLifecycleRepository assignmentLifecycleRepository;

    @Autowired
    private AssignmentQueryService assignmentQueryService;

    public Pair<AssignmentEntity, Assignment> givenACurrentAssignment(Account account) {
        Date startDate = generateDate(23, 1, 2016);

        AccountEntity accountEntity = modelMapper.map(account, AccountEntity.class);

        AssignmentEntity assignmentEntity = anOpenAssignment(accountEntity, "open assignment",startDate);

        assignmentEntity = assignmentLifecycleRepository.save(assignmentEntity);

        Assignment assignment = assignmentQueryService.findCurrentAssignmentForAccount(account).get();

        return new Pair<>(assignmentEntity, assignment);
    }
}
