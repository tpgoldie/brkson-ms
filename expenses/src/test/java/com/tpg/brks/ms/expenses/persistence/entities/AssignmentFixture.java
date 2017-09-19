package com.tpg.brks.ms.expenses.persistence.entities;

import java.util.Date;

public interface AssignmentFixture {
    default AssignmentEntity anOpenAssignment(String description, Date startDate) {
        AssignmentEntity entity = AssignmentEntity.builder()
                .startDate(startDate)
                .status("Open")
                .build();

        entity.setDescription(description);
        return entity;
    }
}
