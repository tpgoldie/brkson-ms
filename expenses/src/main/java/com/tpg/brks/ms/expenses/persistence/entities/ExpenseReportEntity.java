package com.tpg.brks.ms.expenses.persistence.entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "expense_reports_tbl", schema = "brks_expenses")
@Entity(name = "expenseReport")
@Builder
@SequenceGenerator( name = "seq_generator", sequenceName = "expense_reports_seq" )
public class ExpenseReportEntity extends DescriptionEntity {
    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JoinTable(name = "assignments_expense_reports_tbl",
        joinColumns = @JoinColumn(name = "assignment_id"),
        inverseJoinColumns = @JoinColumn(name = "expense_report_id")
    )
    private AssignmentEntity assignment;

    @Getter
    @Setter
    @OneToMany(mappedBy = "expenseReport")
    private List<ExpenseEntity> expenses;

    @Getter
    @Setter
    @Column(name= "period_start")
    private Date periodStart;

    @Getter
    @Setter
    @Column(name = "period_end")
    private Date periodEnd;

    @Getter
    @Setter
    private String status;

}
