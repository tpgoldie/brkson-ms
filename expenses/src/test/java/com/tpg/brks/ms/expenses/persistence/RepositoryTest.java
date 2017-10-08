package com.tpg.brks.ms.expenses.persistence;


import com.tpg.brks.ms.expenses.persistence.entities.*;
import com.tpg.brks.ms.expenses.persistence.repositories.*;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;

@ActiveProfiles({"dev"})
@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class RepositoryTest implements DateGeneration, AccountFixture, AssignmentFixture, ExpenseReportFixture,
    ExpenseFixture {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected AccountLifecycleRepository accountLifecycleRepository;

    @Autowired
    protected AssignmentLifecycleRepository assignmentLifecycleRepository;

    @Autowired
    protected ExpenseReportLifecycleRepository expenseReportLifecycleRepository;

    @Autowired
    protected ExpenseLifecycleRepository expenseLifecycleRepository;

    @Autowired
    protected ExpenseReportQueryRepository expenseReportQueryRepository;

    @Autowired
    protected ExpenseQueryRepository expenseQueryRepository;

    protected AccountEntity givenAnAccount() {
        AccountEntity account = anOpenAccount("John", "Doe", "jdoe");

        return accountLifecycleRepository.save(account);
    }

    protected AssignmentEntity givenAnAssignment(AccountEntity account) {
        Date startDate = generateDate(21,4,2017);

        AssignmentEntity assignment = anOpenAssignment(account, "assignment 1", startDate);

        return assignmentLifecycleRepository.save(assignment);
    }

    protected AssignmentEntity givenCurrentAssignment(AccountEntity account) {
        Date startDate = generateDate(21,4,2017);

        AssignmentEntity assignment = anOpenAssignment(account, "current assignment", startDate);

        return assignmentLifecycleRepository.save(assignment);
    }

    protected AssignmentEntity givenPreviousAssignment(AccountEntity account) {
        Date startDate = generateDate(21,4,2016);
        Date endDate = generateDate(21,7,2016);

        AssignmentEntity assignment = aClosedAssignment(account, "previous assignment", startDate, endDate);

        return assignmentLifecycleRepository.save(assignment);
    }

    protected ExpenseReportEntity givenAnExpenseReport(AssignmentEntity assignment, List<ExpenseEntity> expenses) {

        ExpenseReportEntity report = anExpenseReport(assignment, "report 1", expenses);

        return expenseReportLifecycleRepository.save(report);
    }

    protected ExpenseEntity givenAnExpense() {
        Date expenseDate = generateDate(10, 3, 2017);

        ExpenseEntity expense = aPendingExpense("lunch", expenseDate, SUBSISTENCE, new BigDecimal("23.75"));

        return expenseLifecycleRepository.save(expense);
    }
}
