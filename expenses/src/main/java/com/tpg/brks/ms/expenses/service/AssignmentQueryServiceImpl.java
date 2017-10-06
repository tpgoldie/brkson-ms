package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentQueryRepository;
import com.tpg.brks.ms.expenses.service.converters.AssignmentConverting;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentQueryServiceImpl implements AssignmentQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentQueryServiceImpl.class);

    private AssignmentQueryRepository assignmentQueryRepository;

    private final AssignmentConverting assignmentConverting;

    @Autowired
    public AssignmentQueryServiceImpl(AssignmentQueryRepository assignmentQueryRepository, AssignmentConverting assignmentConverting) {

        this.assignmentQueryRepository = assignmentQueryRepository;

        this.assignmentConverting = assignmentConverting;
    }

    @Override
    public Optional<Assignment> findCurrentAssignmentForAccount(Account account) {
        Optional<AssignmentEntity> found = assignmentQueryRepository.findCurrentAssignmentByAccount(account.getId());

        return found.map(entity -> assignmentConverting.convert(entity));
    }
}
