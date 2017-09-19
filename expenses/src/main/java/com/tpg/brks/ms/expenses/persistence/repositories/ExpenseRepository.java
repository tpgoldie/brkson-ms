package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import org.springframework.data.repository.Repository;

public interface ExpenseRepository extends Repository<ExpenseEntity, Long> {
}
