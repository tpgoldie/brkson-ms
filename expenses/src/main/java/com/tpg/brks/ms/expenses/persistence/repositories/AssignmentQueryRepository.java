package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentQueryRepository extends AssignmentRepository {
    Optional<AssignmentEntity> findById(Long id);
}