package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.AssignmentStatus;

import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.AssignmentStatus.CLOSED;
import static com.tpg.brks.ms.expenses.domain.AssignmentStatus.OPEN;

public interface AssignmentFixture {
    default AssignmentEntity anAssignment(AccountEntity account, String description, Date startDate, Date endDate,
                                          AssignmentStatus status) {
        AssignmentEntity entity = new AssignmentEntity();

        entity.setAccount(account);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setStatus(status);
        entity.setDescription(description);

        return entity;

    }

    default AssignmentEntity anOpenAssignment(AccountEntity account, String description, Date startDate) {
        return anAssignment(account, description, startDate, null, OPEN);
    }

   default AssignmentEntity aClosedAssignment(AccountEntity account, String description, Date startDate, Date endDate) {
       return anAssignment(account, description, startDate, endDate, CLOSED);
    }
}
