package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseQueryRepository extends ExpenseRepository {
    Optional<ExpenseEntity> findById(@Param("id") Long id);
}
