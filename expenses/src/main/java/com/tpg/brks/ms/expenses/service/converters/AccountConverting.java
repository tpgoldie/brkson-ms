package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import org.springframework.core.convert.converter.Converter;

public interface AccountConverting extends Converter<AccountEntity, Account> {
    @Override
    Account convert(AccountEntity accountEntity);
}
