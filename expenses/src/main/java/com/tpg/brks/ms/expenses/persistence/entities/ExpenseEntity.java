package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.ExpenseStatus;
import com.tpg.brks.ms.expenses.domain.ExpenseType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Table(name = "expenses_tbl", schema = "brks_expenses")
@Entity(name = "expense")
@Getter
@Setter
@SequenceGenerator( name = "seq_generator", sequenceName = "expenses_seq" )
public class ExpenseEntity extends DescriptionEntity {
    public ExpenseEntity() {}

    @ManyToMany
    @JoinTable(name = "expense_reports_expenses_tbl",
        joinColumns = @JoinColumn(name = "expense_report_id"),
        inverseJoinColumns = @JoinColumn(name = "expense_id")
    )
    private List<ExpenseReportEntity> expenseReports;

    @NotNull
    private Date expenseDate;

    @NotNull
    private Date dateEntered;

    @NotNull
    @Column(name = "expense_amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "expense_type")
    @Convert(converter = ExpenseTypeConverter.class)
    private ExpenseType expenseType;

    @NotNull
    @Column(name = "expense_status")
    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    @Column(name = "attached_filename", unique = true)
    private String attachedFilename;

    @Column(name= "attached_receipt")
    private Blob attachedReceipt;
}
