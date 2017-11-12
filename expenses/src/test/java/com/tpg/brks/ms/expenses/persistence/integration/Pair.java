package com.tpg.brks.ms.expenses.persistence.integration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<T, U> {
    private T first;

    private U second;
}
