package com.tpg.brks.ms.expenses.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class Assignment extends Identifier {
    @NonNull
    @NotNull
    private Account account;

    private String description;

    private Date startDate;

    private Date endDate;

    private AssignmentStatus status;
}
