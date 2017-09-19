package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Date;

@Table(name = "expenses_tbl", schema = "brks_expenses")
@Entity(name = "expense")
@Builder
@SequenceGenerator( name = "seq_generator", sequenceName = "expenses_seq" )
public class ExpenseEntity extends DescriptionEntity {
    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JoinTable(name = "expense_reports_expenses_tbl",
        joinColumns = @JoinColumn(name = "expense_report_id"),
        inverseJoinColumns = @JoinColumn(name = "expense_id")
    )
    private ExpenseReportEntity expenseReport;

    @Getter
    @Setter
    @NotNull
    private Date expenseDate;

    @Getter
    @Setter
    @NotNull
    private Date dateEntered;

    @Getter
    @Setter
    @NotNull
    @Column(name = "expense_type")
    private String expenseType;

    @Getter
    @Setter
    @NotNull
    private String status;

    @Getter
    @Setter
    @Column(name = "attached_filename", unique = true)
    private String attachedFilename;

    @Getter
    @Setter
    @Column(name= "attached_receipt")
    private Blob attachedReceipt;
}
