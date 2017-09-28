package com.tpg.brks.ms.expenses.domain;

import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.AssignmentStatus.OPEN;

public interface AssignmentFixture {
    default Assignment anOpenAssignment(Account account, Long id, String description, Date startDate, Date endDate) {
        return anAssignment(account, id, description, startDate, endDate, OPEN);
    }

    default Assignment anAssignment(Account account, Long id, String description, Date startDate, Date endDate, AssignmentStatus status) {
        return Assignment.builder()
                .id(id)
                .account(account)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .build();
    }
}
