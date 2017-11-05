package com.tpg.brks.ms.expenses.service.converters;

import com.tpg.brks.ms.expenses.domain.Account;
import com.tpg.brks.ms.expenses.domain.Assignment;
import com.tpg.brks.ms.expenses.domain.Expense;
import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.entities.AssignmentEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseEntity;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.utils.DateFormatting;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ExpenseReportConverter extends DomainConverter implements ExpenseReportConverting, DateFormatting {

    private AssignmentConverter assignmentConverter;

    private ExpenseConverter expenseConverter;

    public ExpenseReportConverter() {

        assignmentConverter = new AssignmentConverter(new AccountConverter());

        TypeMap<ExpenseReportEntity, ExpenseReport> typeMap = modelMapper.createTypeMap(ExpenseReportEntity.class, ExpenseReport.class);

        typeMap.addMappings(mapper -> mapper.map(ExpenseReportEntity::getAssignment, ExpenseReport::setAssignment));

        Converter<Date, String> dateToString =
                ctx -> ctx.getSource() == null ? null : toDdMmYyyyFormat(ctx.getSource());

        typeMap.addMappings(mapper -> mapper.using(dateToString).map(src -> src.getPeriod().getStartDate(), ExpenseReport::setPeriodStart));
        typeMap.addMappings(mapper -> mapper.using(dateToString).map(src -> src.getPeriod().getEndDate(), ExpenseReport::setPeriodEnd));

        Converter<AssignmentEntity, Assignment> assignmentEntityToDto =
                ctx -> ctx.getSource() == null ? null : assignmentConverter.convert(ctx.getSource());

        typeMap.addMappings(mapper -> mapper.using(assignmentEntityToDto).map(ExpenseReportEntity::getAssignment, ExpenseReport::setAssignment));

        expenseConverter = new ExpenseConverter();

        Converter<List<ExpenseEntity>, List<Expense>> expenseEntityToDto =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().stream().map(e -> expenseConverter.convert(e)).collect(toList());

        typeMap.addMappings(mapper -> mapper.using(expenseEntityToDto).map(ExpenseReportEntity::getExpenses, ExpenseReport::setExpenses));
    }

    @Override
    public ExpenseReport convert(ExpenseReportEntity expenseReportEntity) {
        return modelMapper.map(expenseReportEntity, ExpenseReport.class);
    }
}
