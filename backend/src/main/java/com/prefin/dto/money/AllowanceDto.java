package com.prefin.dto.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllowanceDto {
    private Long id;

    private int allowanceAmount;

    private Long payDay;

    private int loanAmount;

    private Parent parent;
    private Child child;
}
