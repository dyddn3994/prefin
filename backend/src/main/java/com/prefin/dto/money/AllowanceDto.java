package com.prefin.dto.money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllowanceDto {

    private Long payday;
    private int allowanceAmount;

    private Long parentId;
    private Long childId;
}
