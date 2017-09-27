package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "assignments_tbl", schema = "brks_expenses")
@Entity(name = "assignment")
@SequenceGenerator( name = "seq_generator", sequenceName = "assignments_seq" )
@Builder
public class AssignmentEntity extends DescriptionEntity {

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JoinTable(name = "accounts_assignments_tbl",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id")
    )
    private AccountEntity account;

    @Getter
    @Setter
    @NotNull
    @Column(name="start_date")
    private Date startDate;

    @Getter
    @Setter
    @Column(name="end_date")
    private Date endDate;

    @Getter
    @Setter
    @NotNull
    @Column(name = "assign_status")
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @Getter
    @Setter
    @OneToMany(mappedBy = "assignment")
    private List<ExpenseReportEntity> expenseReports;
}
