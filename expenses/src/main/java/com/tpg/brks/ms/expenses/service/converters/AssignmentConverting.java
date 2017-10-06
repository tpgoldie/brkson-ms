package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import org.springframework.core.convert.converter.Converter;

public interface AssignmentConverting extends Converter<AssignmentEntity, Assignment> {
    @Override
    Assignment convert(AssignmentEntity assignmentEntity);
}
