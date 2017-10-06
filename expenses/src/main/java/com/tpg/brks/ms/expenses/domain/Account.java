package com.tpg.brks.ms.expenses.domain;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends Identifier {
    private String firstName;

    private String lastName;

    private String username;

    private AccountStatus status;

    public Account() {}
}
