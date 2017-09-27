package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity(name = "account")
@Table(name = "accounts_tbl")
@SequenceGenerator( name = "seq_generator", sequenceName = "accounts_seq" )
@Builder
public class AccountEntity extends BaseEntity {
    @Getter
    @Setter
    @Column(name="first_name")
    private String firstName;

    @Getter
    @Setter
    @Column(name="last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name="user_name")
    private String username;

    @Getter
    @Setter
    @Column(name="account_status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Getter
    @Setter
    @OneToMany(mappedBy = "account")
    private List<AssignmentEntity> assignments;
}
