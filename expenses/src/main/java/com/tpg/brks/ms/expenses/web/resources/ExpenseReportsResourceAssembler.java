package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseReportQueryController;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ExpenseReportsResourceAssembler implements ResourceAssembler<List<ExpenseReport>, ResourceSupport> {
    private final ModelMapper modelMapper = new ModelMapper();

    private final WebApplicationUser webApplicationUser;

    public ExpenseReportsResourceAssembler(WebApplicationUser webApplicationUser) {
        this.webApplicationUser = webApplicationUser;
    }

    @Override
    public ResourceSupport toResource(List<ExpenseReport> expenseReports) {
//        List<ExpenseReport> methodLinkBuilder = methodOn(ExpenseReportQueryController.class).getExpenseReports(webApplicationUser);

        List<Link> links = expenseReports.stream().map(this::toLink).collect(toList());

        ResourceSupport resource = new ResourceSupport();

        resource.add(linkTo(ExpenseReportQueryController.class)
            .slash("expenseReports")
                .withRel("expenseReports")
                .withSelfRel());

        resource.add(links);

        return resource;
    }

    private Link toLink(ExpenseReport report) {
        ExpenseReportResource resource = modelMapper.map(report, ExpenseReportResource.class);

        Link selfLink = linkTo(ExpenseReportQueryController.class).slash(report.getId()).withSelfRel();
        resource.add(selfLink);

        return selfLink;
    }
}
