package com.tpg.brks.ms.expenses.web;

import com.tpg.brks.ms.expenses.domain.*;
import com.tpg.brks.ms.expenses.service.AccountQueryService;
import com.tpg.brks.ms.expenses.service.AssignmentQueryService;
import com.tpg.brks.ms.expenses.service.ExpenseReportQueryService;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;
import com.tpg.brks.ms.utils.DateGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;

@AutoConfigureMockMvc
public abstract class BaseGivenTest implements Given, DateGeneration, WebApplicationUserFixture,
        AccountFixture, AssignmentFixture, ExpenseReportFixture {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AssignmentQueryService assignmentQueryService;

    @MockBean
    protected AccountQueryService accountQueryService;

    @MockBean
    protected ExpenseReportQueryService expenseReportQueryService;

    @Override
    public Account givenAnAccount() {
        return johnDoeAccount();
    }

    @Override
    public Assignment givenAnAssignment(Account account) {
        return anOpenAssignment(account, "Assignment 1", generateDate(15, 4, 2016),
                generateDate(15, 4, 2017));
    }

    @Override
    public List<ExpenseReport> givenExpenseReports(Assignment assignment, Date periodStart, Date periodEnd) {
        return singletonList(anExpenseReport(assignment, "report 1", periodStart, periodEnd, "PENDING"));
    }
}
