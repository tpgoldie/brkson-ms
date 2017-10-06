package com.tpg.brks.ms.expenses.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Assignment extends Identifier {
    @NonNull
    @NotNull
    private Account account;

    private String description;

    private Date startDate;

    private Date endDate;

    private AssignmentStatus status;

    public Assignment() {}
}
