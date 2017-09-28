package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseReportQueryRepository extends ExpenseReportRepository {
    List<ExpenseReportEntity> findByAssignmentId(Long id);
}
