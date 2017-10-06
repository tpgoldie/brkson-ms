package com.tpg.brks.ms.expenses.web.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseStatus;
import com.tpg.brks.ms.expenses.domain.ExpenseType;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseQueryController;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
@Relation(value = "expense", collectionRelation = "expenses")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseResource extends IdentifiedResource<Expense> implements DateGeneration {

    public ExpenseResource(Expense expense) {
        super(expense);

        setResourceId(expense.getId());
    }

    public void link() {
        add(linkTo(methodOn(ExpenseQueryController.class).getExpense(null, getResourceId()))
                .withSelfRel());
    }
}
