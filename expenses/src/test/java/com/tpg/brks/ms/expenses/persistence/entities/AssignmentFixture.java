package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Date;

public interface AssignmentFixture {
    default AssignmentEntity anOpenAssignment(AccountEntity account, String description, Date startDate) {
        AssignmentEntity entity = AssignmentEntity.builder()
                .account(account)
                .startDate(startDate)
                .status("Open")
                .build();

        entity.setDescription(description);
        return entity;
    }
}
