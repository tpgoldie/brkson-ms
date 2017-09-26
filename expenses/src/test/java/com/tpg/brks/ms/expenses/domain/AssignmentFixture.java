package com.tpg.brks.ms.expenses.domain;

import java.util.Date;

public interface AssignmentFixture {
    default Assignment anOpenAssignment(Account account, String description, Date startDate, Date endDate) {
        return Assignment.builder()
                .account(account)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .status("OPEN")
                .build();
    }
}
