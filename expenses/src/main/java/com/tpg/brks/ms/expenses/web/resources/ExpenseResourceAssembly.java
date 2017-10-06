package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import org.springframework.hateoas.ResourceAssembler;

import java.util.List;

public interface ExpenseResourceAssembly extends ResourceAssembler<Expense, ExpenseResource> {
    @Override
    ExpenseResource toResource(Expense expense);

    List<ExpenseResource> toResources(List<Expense> expenses);
}
