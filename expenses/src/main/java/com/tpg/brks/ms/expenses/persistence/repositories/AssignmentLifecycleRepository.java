package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentLifecycleRepository extends AssignmentRepository {
    AssignmentEntity save(AssignmentEntity assignment);
}
