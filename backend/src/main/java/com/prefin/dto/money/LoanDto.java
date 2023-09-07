package com.prefin.dto.money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    private int loanAmount;
    private Long loanDate;

    private Long parentId;
    private Long childId;

}
