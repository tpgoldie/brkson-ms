package com.tpg.brks.ms.expenses.domain;

import lombok.Getter;

@Getter
public enum ExpenseType {
    SUBSISTENCE("Subsistence"), TRAVEL("Travel"), TRAINING("Training"), MISCELLANEOUS("Miscellaneous"), NOT_DEFINED("Not Defined");

    private final String label;

    ExpenseType(String label) {
        this.label = label;
    }
}
