package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import org.modelmapper.TypeMap;

public class AccountConverter extends DomainConverter implements AccountConverting {

    public AccountConverter() {
        TypeMap<AccountEntity, Account> typeMap = modelMapper.createTypeMap(AccountEntity.class, Account.class);

        typeMap.addMappings(mapper -> mapper.map(src -> src.getName().getFirstName(), Account::setFirstName));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getName().getLastName(), Account::setLastName));
    }

    @Override
    public Account convert(AccountEntity accountEntity) {
        return modelMapper.map(accountEntity, Account.class);
    }
}
