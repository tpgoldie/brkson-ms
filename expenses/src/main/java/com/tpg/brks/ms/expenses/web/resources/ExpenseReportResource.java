package com.tpg.brks.ms.expenses.web.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseQueryController;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseReportQueryController;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.core.Relation;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
@Relation(value = "expenseReport", collectionRelation = "expenseReports")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseReportResource extends IdentifiedResource<ExpenseReport> implements DateGeneration {

    @JsonUnwrapped()
    private List<ExpenseResource> expenses;

    public ExpenseReportResource(ExpenseReport expenseReport) {

        super(expenseReport);

        setResourceId(expenseReport.getId());
    }

    void addExpenseResources(List<Expense> expenses) {

        ExpenseResourceAssembly expenseResourceAssembly = new ExpenseResourceAssembler();

        this.expenses = expenseResourceAssembly.toResources(expenses);
    }

    public void link() {
        add(linkTo(methodOn(ExpenseReportQueryController.class).getExpenseReport(null, getContent().getId()))
                .withSelfRel());
    }
}
