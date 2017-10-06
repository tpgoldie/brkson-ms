package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import org.springframework.hateoas.ResourceAssembler;

import java.util.List;

public interface ExpenseReportResourceAssembly extends ResourceAssembler<ExpenseReport, ExpenseReportResource> {
    @Override
    ExpenseReportResource toResource(ExpenseReport report);

    List<ExpenseReportResource> toResources(List<ExpenseReport> reports);
}
