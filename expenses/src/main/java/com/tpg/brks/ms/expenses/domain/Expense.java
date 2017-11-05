package com.tpg.brks.ms.expenses.domain;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;

@Data
@Relation(value = "expense", collectionRelation = "expenses")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense extends Identifier {
    private String dateEntered;

    private String expenseDate;

    private String description;

    private ExpenseType expenseType;

    private ExpenseStatus status;

    private BigDecimal amount;

    private String attachedFilename;
}
