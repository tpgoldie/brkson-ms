package com.tpg.brks.ms.expenses.domain;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ExpenseReport {
    private Long id;

    @NonNull
    @NotNull
    private Assignment assignment;

    private String description;

    private String periodStart;

    private String periodEnd;

    private ExpenseReportStatus status;

    public ExpenseReport() {}
}
