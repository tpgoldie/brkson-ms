package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ExpenseReportRepository extends Repository<ExpenseReportEntity, Long> {
}
