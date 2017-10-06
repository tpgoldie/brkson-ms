package com.tpg.brks.ms.expenses.domain;

import lombok.Value;

@Value
public class Period {
    private final String periodStart;
    private final String periodEnd;

    public Period(String periodStart, String periodEnd) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }
}
