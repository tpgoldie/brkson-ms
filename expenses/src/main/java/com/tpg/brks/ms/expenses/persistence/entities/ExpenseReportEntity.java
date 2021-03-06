package com.tpg.brks.ms.expenses.persistence.entities;


import com.tpg.brks.ms.expenses.domain.ExpenseReportStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Table(name = "expense_reports_tbl", schema = "brks_expenses")
@Entity(name = "expenseReport")
@Getter
@Setter
@SequenceGenerator( name = "seq_generator", sequenceName = "expense_reports_seq" )
public class ExpenseReportEntity extends DescriptionEntity {

    public ExpenseReportEntity() {}

    @NotNull
    @ManyToOne
    @JoinTable(name = "assignments_expense_reports_tbl",
        joinColumns = @JoinColumn(name = "assignment_id"),
        inverseJoinColumns = @JoinColumn(name = "expense_report_id")
    )
    private AssignmentEntity assignment;

    @ManyToMany(mappedBy = "expenseReports", fetch = EAGER)
    private List<ExpenseEntity> expenses;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="startDate", column=@Column(name="period_start")),
            @AttributeOverride(name="endDate", column=@Column(name="period_end"))
    })
    private Period period;

    @NotNull
    @Column(name = "report_status")
    @Enumerated(EnumType.STRING)
    private ExpenseReportStatus status;
}
