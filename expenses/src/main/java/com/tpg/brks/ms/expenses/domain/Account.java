package com.tpg.brks.ms.expenses.domain;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Account {
    private String firstName;

    private String lastName;

    private String username;

    private AccountStatus status;
}
