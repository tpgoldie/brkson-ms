package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@RequiredArgsConstructor
public abstract class DescriptionEntity extends BaseEntity {
    @Getter
    @Setter
    @NotNull
    private String description;
}
