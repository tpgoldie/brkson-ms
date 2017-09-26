package com.tpg.brks.ms.expenses.web.controllers;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResource;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportsResource;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportsResourceAssembler;
import org.apache.catalina.loader.ResourceEntry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ExpenseReportQueryController {

    private AssignmentQueryService assignmentQueryService;

    private AccountQueryService accountQueryService;

    private ExpenseReportQueryService expenseReportQueryService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ExpenseReportQueryController(AssignmentQueryService assignmentQueryService, AccountQueryService accountQueryService,
                                        ExpenseReportQueryService expenseReportQueryService) {
        this.assignmentQueryService = assignmentQueryService;

        this.expenseReportQueryService = expenseReportQueryService;

        this.accountQueryService = accountQueryService;
    }

    @GetMapping(value = "/expenseReports", produces = {APPLICATION_JSON_UTF8_VALUE})
    @Secured("ROLE_EXPENSE_USER")
    @PreAuthorize("authenticated")
    public ResponseEntity<List<ExpenseReport>> handleExpenseReports(@AuthenticationPrincipal  WebApplicationUser user) {
        Optional<Account> foundAccount = accountQueryService.findAccountByUsername(user.getUsername());

        return foundAccount.map(this::getExpenseReports).orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<List<ExpenseReport>> getExpenseReports(Account account) {
        Optional<Assignment> foundAssignment = assignmentQueryService.findCurrentAssignmentForAccount(account);

        List<ExpenseReport> expenseReports = expenseReportQueryService.getExpenseReportsForAssignment(foundAssignment.get());

        return ResponseEntity.ok(expenseReports);
    }

    @GetMapping(value = "/expenseReports/{reportId}", produces = {MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<ExpenseReport> getExpenseReport(@AuthenticationPrincipal  WebApplicationUser user,
                                                                  @PathVariable String reportId) {
        return null;
    }
}
