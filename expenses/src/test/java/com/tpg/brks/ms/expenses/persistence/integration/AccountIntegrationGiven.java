package com.tpg.brks.ms.expenses.persistence.integration;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.AccountLifecycleRepository;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@Value
@TestComponent
public class AccountIntegrationGiven implements PersistenceGiven {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AccountLifecycleRepository accountLifecycleRepository;

    public Pair<AccountEntity, Account> givenAnAccount() {
        AccountEntity entity = anOpenAccount("John", "Doe", "jdoe");

        entity = accountLifecycleRepository.save(entity);

        Account dto = modelMapper.map(entity, Account.class);

        return new Pair<>(entity, dto);
    }
}
