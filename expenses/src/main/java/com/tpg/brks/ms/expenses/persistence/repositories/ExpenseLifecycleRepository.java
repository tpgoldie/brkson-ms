package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseLifecycleRepository extends ExpenseRepository {
    ExpenseEntity save(ExpenseEntity entity);
}
