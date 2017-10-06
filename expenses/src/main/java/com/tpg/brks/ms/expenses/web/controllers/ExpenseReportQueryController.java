package com.tpg.brks.ms.expenses.web.controllers;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResource;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResourceAssembly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@ExposesResourceFor(ExpenseReport.class)
public class ExpenseReportQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseReportQueryController.class);

    private AssignmentQueryService assignmentQueryService;

    private AccountQueryService accountQueryService;

    private ExpenseReportQueryService expenseReportQueryService;

    private final ExpenseReportResourceAssembly expenseReportResourceAssembly;
    
    @Autowired
    public ExpenseReportQueryController(AssignmentQueryService assignmentQueryService, AccountQueryService accountQueryService,
                                        ExpenseReportQueryService expenseReportQueryService, ExpenseReportResourceAssembly expenseReportResourceAssembly) {
        this.assignmentQueryService = assignmentQueryService;

        this.expenseReportQueryService = expenseReportQueryService;

        this.accountQueryService = accountQueryService;

        this.expenseReportResourceAssembly = expenseReportResourceAssembly;
    }

    @GetMapping(value = "/expenseReports", produces = {HAL_JSON_VALUE})
    @Secured("ROLE_EXPENSE_USER")
    @PreAuthorize("authenticated")
    public ResponseEntity<List<ExpenseReportResource>> getExpenseReports(@AuthenticationPrincipal WebApplicationUser webApplicationUser) {

        LOGGER.debug("User {} authenticated ...", webApplicationUser.getUsername());

        Optional<Account> foundAccount = accountQueryService.findAccountByUsername(webApplicationUser.getUsername());

        return foundAccount.map(this::getExpenseReports).orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<List<ExpenseReportResource>> getExpenseReports(Account account) {
        Optional<Assignment> foundAssignment = assignmentQueryService.findCurrentAssignmentForAccount(account);

        return foundAssignment.map(this::getExpenseReportsUsingAssignment).orElse(ResponseEntity.notFound().build());
    }
    
    private ResponseEntity<List<ExpenseReportResource>> getExpenseReportsUsingAssignment(Assignment assignment) {
        List<ExpenseReport> expenseReports = expenseReportQueryService.getExpenseReportsForAssignment(assignment.getId());

        List<ExpenseReportResource> resources = expenseReportResourceAssembly.toResources(expenseReports);
        
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/expenseReports/{reportId}", produces = {HAL_JSON_VALUE})
    @Secured("ROLE_EXPENSE_USER")
    @PreAuthorize("authenticated")
    public ResponseEntity<ExpenseReportResource> getExpenseReport(@AuthenticationPrincipal  WebApplicationUser webApplicationUser,
                                                                  @PathVariable Long reportId) {

        LOGGER.debug("user {} authenticated ...", webApplicationUser.getUsername());

        Optional<ExpenseReport> expenseReport = expenseReportQueryService.getExpenseReport(reportId);

        ExpenseReportResource resource = expenseReportResourceAssembly.toResource(expenseReport.get());

        return ResponseEntity.ok(resource);
    }
}
