package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@RequiredArgsConstructor
@Getter
@Setter
public abstract class DescriptionEntity extends BaseEntity {
    @NotNull
    private String description;
}
