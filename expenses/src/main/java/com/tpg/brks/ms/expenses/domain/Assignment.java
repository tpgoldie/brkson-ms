package com.tpg.brks.ms.expenses.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@Builder
public class Assignment {
    private Long id;

    @NonNull
    @NotNull
    private Account account;

    private String description;

    private Date startDate;

    private Date endDate;

    private AssignmentStatus status;
}
