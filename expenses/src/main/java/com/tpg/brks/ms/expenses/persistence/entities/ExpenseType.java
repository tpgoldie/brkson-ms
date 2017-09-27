package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Getter;

@Getter
public enum ExpenseType {
    SUBSISTENCE("Subsistence"), TRAVEL("Travel"), TRAINING("Training"), MISCELLANEOUS("Miscellaneous"), NOT_DEFINED("Not Defined");

    private final String label;

    ExpenseType(String label) {
        this.label = label;
    }
}
