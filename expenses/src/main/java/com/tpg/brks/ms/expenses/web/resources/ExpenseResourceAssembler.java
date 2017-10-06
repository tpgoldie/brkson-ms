package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.Expense;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseResourceAssembler extends DomainResourceAssembler<Expense, ExpenseResource> implements ExpenseResourceAssembly {

    public ExpenseResourceAssembler() {
        super(Expense.class, ExpenseResource.class);

        TypeMap<Expense, ExpenseResource> typeMap = modelMapper.createTypeMap(Expense.class, ExpenseResource.class);
        typeMap.addMapping(Expense::getId, ExpenseResource::setResourceId);
    }

    @Override
    public ExpenseResource toResource(Expense expense) {
        ExpenseResource resource = new ExpenseResource(expense);

        resource.link();

        return resource;
    }

    @Override
    public List<ExpenseResource> toResources(List<Expense> entities) {
        return super.toResources(entities);
    }
}
