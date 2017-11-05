package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignmentConverter extends DomainConverter implements AssignmentConverting {
    private final AccountConverting accountConverting;

    @Autowired
    public AssignmentConverter(AccountConverting accountConverting) {

        this.accountConverting = accountConverting;

        Converter<AccountEntity, Account> entityToDomainConverter = ctx -> ctx.getSource() != null ?
                accountConverting.convert(ctx.getSource()) : null;

        TypeMap<AssignmentEntity, Assignment> typeMap = modelMapper.createTypeMap(AssignmentEntity.class, Assignment.class);

        typeMap.addMappings(modelMapper -> modelMapper.using(entityToDomainConverter)
                .map(AssignmentEntity::getAccount, Assignment::setAccount));

        Converter<AccountEntity, Account> accountEntityToDto =
                ctx -> ctx.getSource() == null ? null : accountConverting.convert(ctx.getSource());

        typeMap.addMappings(mapper -> mapper.using(accountEntityToDto).map(AssignmentEntity::getAccount, Assignment::setAccount));
    }

    @Override
    public Assignment convert(AssignmentEntity assignmentEntity) {
        return modelMapper.map(assignmentEntity, Assignment.class);
    }
}
