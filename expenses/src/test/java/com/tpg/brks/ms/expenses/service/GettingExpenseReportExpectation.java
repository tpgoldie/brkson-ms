package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportQueryRepository;

import static org.mockito.Mockito.verify;

public class GettingExpenseReportExpectation {
    private ExpenseReportQueryRepository expenseReportQueryRepository;

    public static GettingExpenseReportExpectation then() {
        return new GettingExpenseReportExpectation();
    }

    public GettingExpenseReportExpectation expectExpenseReportQueryRepository(ExpenseReportQueryRepository expenseReportQueryRepository) {
        this.expenseReportQueryRepository = expenseReportQueryRepository;

        return this;
    }

    public GettingExpenseReportExpectation toFindExpenseReportById(Long id) {
        verify(expenseReportQueryRepository).findById(id);

        return this;
    }
}
