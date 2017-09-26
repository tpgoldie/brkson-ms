package com.tpg.brks.ms.expenses.web.resources;

import com.tpg.brks.ms.expenses.web.controllers.ExpenseReportQueryController;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@NoArgsConstructor
@Getter
@Setter
@Relation(value = "expenseReport", collectionRelation = "expenseReports")
public class ExpenseReportResource extends ResourceSupport {
    @JsonProperty
    private Long expenseReportId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("periodStart")
    private Date periodStart;

    @JsonProperty("periodEnd")
    private Date periodEnd;

    @JsonProperty("status")
    private String status;

    @JsonCreator
    public ExpenseReportResource(String description, Date periodStart, Date periodEnd, String status) {
        this.description = description;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.status = status;

        link();
    }

    private void link() {
        add(linkTo(ExpenseReportQueryController.class).slash(getExpenseReportId()).withSelfRel());
    }
}
