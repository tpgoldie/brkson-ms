package com.tpg.brks.ms.expenses.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class ExpenseReport {
    private Long id;

    @NonNull
    @NotNull
    private Assignment assignment;

    private String description;

    private String periodStart;

    private String periodEnd;

    private String status;
}
