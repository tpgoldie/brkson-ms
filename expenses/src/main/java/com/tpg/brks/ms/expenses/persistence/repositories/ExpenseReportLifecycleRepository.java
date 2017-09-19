package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseReportLifecycleRepository extends ExpenseReportRepository {
    ExpenseReportEntity save(ExpenseReportEntity expenseReport);
}
