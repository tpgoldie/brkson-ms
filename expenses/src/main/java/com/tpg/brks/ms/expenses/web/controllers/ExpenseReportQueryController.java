package com.tpg.brks.ms.expenses.web.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResource;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResourceAssembler;

@RestController
public class ExpenseReportQueryController {

    private AssignmentQueryService assignmentQueryService;

    private AccountQueryService accountQueryService;

    private ExpenseReportQueryService expenseReportQueryService;

    private final ExpenseReportResourceAssembler expenseReportResourceAssembler;
    
    @Autowired
    public ExpenseReportQueryController(AssignmentQueryService assignmentQueryService, AccountQueryService accountQueryService,
                                        ExpenseReportQueryService expenseReportQueryService) {
        this.assignmentQueryService = assignmentQueryService;

        this.expenseReportQueryService = expenseReportQueryService;

        this.accountQueryService = accountQueryService;
        
        expenseReportResourceAssembler = new ExpenseReportResourceAssembler();
    }

    @GetMapping(value = "/expenseReports", produces = {APPLICATION_JSON_UTF8_VALUE})
    @Secured("ROLE_EXPENSE_USER")
    @PreAuthorize("authenticated")
    public ResponseEntity<List<ExpenseReportResource>> handleExpenseReports(@AuthenticationPrincipal  WebApplicationUser user) {
        Optional<Account> foundAccount = accountQueryService.findAccountByUsername(user.getUsername());

        return foundAccount.map(this::getExpenseReports).orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<List<ExpenseReportResource>> getExpenseReports(Account account) {
        Optional<Assignment> foundAssignment = assignmentQueryService.findCurrentAssignmentForAccount(account);

        List<ExpenseReport> expenseReports = expenseReportQueryService.getExpenseReportsForAssignment(foundAssignment.get().getId());

        List<ExpenseReportResource> resources = expenseReportResourceAssembler.toResources(expenseReports);
        
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/expenseReports/{reportId}", produces = {MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<ExpenseReport> getExpenseReport(@AuthenticationPrincipal  WebApplicationUser user,
                                                                  @PathVariable String reportId) {
        return null;
    }
}
