package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentFixture;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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

    public Assignment givenACurrentAssignment(Account account) {
        Date startDate = generateDate(23, 1, 2016);

        AccountEntity accountEntity = modelMapper.map(account, AccountEntity.class);

        AssignmentEntity assignmentEntity = anOpenAssignment(accountEntity, "open assignment",startDate);

        assignmentLifecycleRepository.save(assignmentEntity);

        return assignmentQueryService.findCurrentAssignmentForAccount(account).get();
    }
}
