package com.tpg.brks.ms.expenses.persistence.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Data
@NoArgsConstructor
public class Period {
    private Date startDate;

    private Date endDate;
}
