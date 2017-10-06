package com.tpg.brks.ms.expenses.service;

import com.tpg.brks.ms.expenses.domain.ExpenseReport;
import com.tpg.brks.ms.expenses.persistence.entities.ExpenseReportEntity;
import com.tpg.brks.ms.expenses.persistence.repositories.ExpenseReportQueryRepository;
import com.tpg.brks.ms.expenses.utils.DateGeneration;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ExpenseReportQueryServiceImpl implements ExpenseReportQueryService, DateGeneration {

    private final ExpenseReportQueryRepository expenseReportQueryRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final TypeMap<ExpenseReportEntity,ExpenseReport> typeMap;

    @Autowired
    public ExpenseReportQueryServiceImpl(ExpenseReportQueryRepository expenseReportQueryRepository) {

        this.expenseReportQueryRepository = expenseReportQueryRepository;

        Converter<Date, String> dateToStringConverter = ctx -> ctx.getSource() != null ? toDdMmYyyyFormat(ctx.getSource()) : "";
        typeMap = modelMapper.createTypeMap(ExpenseReportEntity.class, ExpenseReport.class);

        typeMap.addMappings(modelMapper -> modelMapper.using(dateToStringConverter)
                .map(src -> src.getPeriod().getStartDate(), ExpenseReport::setPeriodStart));

        typeMap.addMappings(modelMapper -> modelMapper.using(dateToStringConverter)
                .map(src -> src.getPeriod().getEndDate(), ExpenseReport::setPeriodEnd));
    }

    @Override
    public List<ExpenseReport> getExpenseReportsForAssignment(Long assignmentId) {
        List<ExpenseReportEntity> found = expenseReportQueryRepository.findByAssignmentId(assignmentId);

        return found.stream().map(entity -> modelMapper.map(entity, ExpenseReport.class)).collect(toList());
    }

    @Override
    public Optional<ExpenseReport> getExpenseReport(Long reportId) {
        return null;
    }
}
