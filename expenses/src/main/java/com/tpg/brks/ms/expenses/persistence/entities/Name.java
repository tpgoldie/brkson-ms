package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class Name {
    private String firstName;

    private String lastName;
}
