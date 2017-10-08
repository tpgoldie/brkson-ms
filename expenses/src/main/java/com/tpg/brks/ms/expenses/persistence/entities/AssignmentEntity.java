package com.tpg.brks.ms.expenses.persistence.entities;

import com.tpg.brks.ms.expenses.domain.AssignmentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "assignments_tbl", schema = "brks_expenses")
@Entity(name = "Assignment")
@SequenceGenerator( name = "seq_generator", sequenceName = "assignments_seq" )
@Getter
@Setter
public class AssignmentEntity extends DescriptionEntity {

    @NotNull
    @ManyToOne
    @JoinTable(name = "accounts_assignments_tbl",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id")
    )
    private AccountEntity account;

    @NotNull
    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @NotNull
    @Column(name = "assign_status")
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @OneToMany(mappedBy = "assignment")
    private List<ExpenseReportEntity> expenseReports;

    public AssignmentEntity() {}
}
