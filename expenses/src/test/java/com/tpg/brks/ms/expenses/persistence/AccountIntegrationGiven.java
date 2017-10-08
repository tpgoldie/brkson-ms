package com.tpg.brks.ms.expenses.persistence;

import com.tpg.brks.ms.expenses.domain.Account;
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

    public Account givenAnAccount() {
        AccountEntity entity = anOpenAccount("John", "Doe", "jdoe");
        entity = accountLifecycleRepository.save(entity);

        return modelMapper.map(entity, Account.class);
    }
}
