package com.tpg.brks.ms.expenses.web.controllers;

import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.service.ExpenseQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUser;
import com.tpg.brks.ms.expenses.web.resources.ExpenseResource;
import com.tpg.brks.ms.expenses.web.resources.ExpenseResourceAssembly;
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

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/expenses")
@ExposesResourceFor(Expense.class)
public class ExpenseQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseQueryController.class);

    private ExpenseQueryService expenseQueryService;

    private ExpenseResourceAssembly expenseResourceAssembly;

    @Autowired
    public ExpenseQueryController(ExpenseQueryService expenseQueryService, ExpenseResourceAssembly expenseResourceAssembly) {
        this.expenseQueryService = expenseQueryService;
        this.expenseResourceAssembly = expenseResourceAssembly;
    }

    @GetMapping(value = "/{expenseId}", produces = {HAL_JSON_VALUE})
    @Secured("ROLE_EXPENSE_USER")
    @PreAuthorize("authenticated")
    public ResponseEntity<ExpenseResource> getExpense(@AuthenticationPrincipal WebApplicationUser webApplicationUser,
                                                      @PathVariable Long expenseId) {

        LOGGER.debug("User {} authenticated ...", webApplicationUser.getUsername());

        Expense expense = expenseQueryService.getExpense(expenseId).get();

        ExpenseResource resource = expenseResourceAssembly.toResource(expense);

        return ResponseEntity.ok(resource);
    }
}
