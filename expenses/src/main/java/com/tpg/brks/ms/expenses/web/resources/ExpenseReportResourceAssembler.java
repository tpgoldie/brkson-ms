package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseReportQueryController;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ExpenseReportResourceAssembler extends ResourceAssemblerSupport<ExpenseReport, ExpenseReportResource> {
    private final ModelMapper modelMapper = new ModelMapper();

    public ExpenseReportResourceAssembler() {
        super(ExpenseReportQueryController.class, ExpenseReportResource.class);
    }

    @Override
    public ExpenseReportResource toResource(ExpenseReport report) {
        ExpenseReportResource resource = modelMapper.map(report, ExpenseReportResource.class);

        resource.add(linkTo(ExpenseReportQueryController.class)
                .slash("expenseReport").slash(report.getId())
                .withSelfRel());

        return resource;
    }
}
