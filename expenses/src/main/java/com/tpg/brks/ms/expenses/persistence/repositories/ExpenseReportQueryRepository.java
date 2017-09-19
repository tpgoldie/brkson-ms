package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseReportQueryRepository extends ExpenseReportRepository {
    Optional<ExpenseReportEntity> findByAssignmentId(Long id);
}
