package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface AssignmentRepository extends Repository<AssignmentEntity, Long> {
}
