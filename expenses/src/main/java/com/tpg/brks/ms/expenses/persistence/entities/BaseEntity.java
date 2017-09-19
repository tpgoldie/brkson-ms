package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {
    @Getter
    @Setter
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    private Long id;
}
