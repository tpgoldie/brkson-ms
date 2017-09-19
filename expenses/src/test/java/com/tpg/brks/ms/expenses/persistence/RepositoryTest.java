package com.tpg.brks.ms.expenses.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public abstract class RepositoryTest {

    @Autowired
    protected TestEntityManager entityManager;
}
