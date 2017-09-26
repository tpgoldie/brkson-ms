package com.tpg.brks.ms.expenses.web.resources;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Relation(value="expenseReports", collectionRelation = "expenseReports")
public class ExpenseReportsResource extends ResourceSupport {

    @JsonCreator
    public ExpenseReportsResource(@JsonProperty("expenseReports") List<ExpenseReportResource> expenseReports) {
        expenseReports.stream().map(resource -> resource.getLink("expenseReport")).collect(toList());
    }
}
