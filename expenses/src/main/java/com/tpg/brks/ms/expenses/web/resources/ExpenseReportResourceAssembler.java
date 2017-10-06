package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseReportResourceAssembler extends DomainResourceAssembler<ExpenseReport, ExpenseReportResource>
        implements ExpenseReportResourceAssembly {

    public ExpenseReportResourceAssembler() {
        super(ExpenseReport.class, ExpenseReportResource.class);
    }

    @Override
    public ExpenseReportResource toResource(ExpenseReport report) {
        ExpenseReportResource resource = new ExpenseReportResource(report);

        resource.link();

        resource.addExpenseResources(report.getExpenses());

        return resource;
    }

    @Override
    public List<ExpenseReportResource> toResources(List<ExpenseReport> reports) {
        return super.toResources(reports);
    }
}
