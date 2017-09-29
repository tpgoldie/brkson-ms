package com.tpg.brks.ms.expenses.domain;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JsonRootName("expenseReport")
@Relation(value="expenseReport", collectionRelation = "expenseReports")
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
