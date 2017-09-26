package com.tpg.brks.ms.expenses.persistence.repositories;

import com.tpg.brks.ms.expenses.persistence.entities.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLifecycleRepository extends AccountRepository {
    AccountEntity save(AccountEntity accountEntity);
}
