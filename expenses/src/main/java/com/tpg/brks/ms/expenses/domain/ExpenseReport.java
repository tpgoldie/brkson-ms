package com.tpg.brks.ms.expenses.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@JsonRootName("expenseReport")
@Relation(value="expenseReport", collectionRelation = "expenseReports")
public class ExpenseReport extends Identifier {

    private Long id;

    @NonNull
    @NotNull
    @JsonIgnore
    private Assignment assignment;

    private String description;

    private String periodStart;

    private String periodEnd;

    private ExpenseReportStatus status;

    @JsonIgnore
    private List<Expense> expenses;

    public ExpenseReport() {}
}
