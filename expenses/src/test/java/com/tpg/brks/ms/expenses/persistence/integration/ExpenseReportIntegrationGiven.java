package com.tpg.brks.ms.expenses.persistence.integration;


import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.PersistenceGiven;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseLifecycleRepository;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportLifecycleRepository;
import com.tpg.brks.ms.expenses.service.converters.ExpenseReportConverter;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import lombok.Data;
import lombok.Value;
import org.junit.Before;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static com.tpg.brks.ms.expenses.domain.ExpenseType.SUBSISTENCE;
import static java.util.Collections.singletonList;

@Data
public class ExpenseReportIntegrationGiven implements DateGeneration, PersistenceGiven {

    private ExpenseReportConverter expenseReportConverter = new ExpenseReportConverter();

    @Autowired
    private ExpenseReportLifecycleRepository expenseReportLifecycleRepository;

    @Autowired
    private ExpenseLifecycleRepository expenseLifecycleRepository;

    public Pair<ExpenseReportEntity, ExpenseReport> givenAnExpenseReport(AssignmentEntity assignment, Date periodStart, Date periodEnd) {
        ExpenseReportEntity expenseReportEntity = anOpenExpenseReport(assignment, "report 1", periodStart, periodEnd);
        expenseReportEntity = expenseReportLifecycleRepository.save(expenseReportEntity);

        ExpenseEntity expenseEntity = aPendingExpense(expenseReportEntity,"expense 1", generateDate(15, 4, 2017),
                generateDate(13, 4, 2017), SUBSISTENCE, new BigDecimal("250.00"));

        expenseEntity = expenseLifecycleRepository.save(expenseEntity);

        expenseReportEntity.setExpenses(singletonList(expenseEntity));

        expenseReportEntity = expenseReportLifecycleRepository.save(expenseReportEntity);

        ExpenseReport expenseReport = expenseReportConverter.convert(expenseReportEntity);

        return new Pair<>(expenseReportEntity, expenseReport);
    }
}
