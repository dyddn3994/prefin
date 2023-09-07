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

    private int allowanceAmount;
    private Long payday;

    private Long parentId;
    private Long childId;
}
