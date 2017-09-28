package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.AssignmentStatus.OPEN;

public interface AssignmentFixture {
    default AssignmentEntity anOpenAssignment(AccountEntity account, String description, Date startDate) {
        AssignmentEntity entity = AssignmentEntity.builder()
                .account(account)
                .startDate(startDate)
                .status(OPEN)
                .build();

        entity.setDescription(description);
        return entity;
    }
}
