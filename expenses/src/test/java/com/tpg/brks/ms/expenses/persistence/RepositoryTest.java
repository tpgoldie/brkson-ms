package com.tpg.brks.ms.expenses.persistence;


import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AccountFixture;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentFixture;
import com.tpg.brks.ms.expenses.persistence.repositories.AccountLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.AssignmentLifecycleRepository;
import com.tpg.brks.ms.utils.DateGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

@ActiveProfiles({"dev"})
public abstract class RepositoryTest implements DateGeneration, AccountFixture, AssignmentFixture {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected AccountLifecycleRepository accountLifecycleRepository;

    @Autowired
    protected AssignmentLifecycleRepository assignmentLifecycleRepository;

    protected AccountEntity givenAnAccount() {
        AccountEntity account = anOpenAccount("John", "Doe", "jdoe");

        return accountLifecycleRepository.save(account);
    }

    protected AssignmentEntity givenAnAssignment(AccountEntity account) {
        Date startDate = generateDate(21,4,2017);

        AssignmentEntity assignment = anOpenAssignment(account, "assignment 1", startDate);

        return assignmentLifecycleRepository.save(assignment);
    }
}
