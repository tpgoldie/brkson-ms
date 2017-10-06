package com.tpg.brks.ms.expenses.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Identifier implements Identifiable<Long> {
    private Long id;
}
