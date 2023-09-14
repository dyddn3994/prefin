package com.prefin.dto.money;

import com.prefin.domain.money.LoanHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
    private Long id;

    private int loanAmount;
    private Boolean isAccepted;

    private Long parentId;
    private Long childId;

    public static LoanDto fromEntity(LoanHistory loanHistory) {
        return LoanDto.builder()
                .id(loanHistory.getId())
                .loanAmount(loanHistory.getLoanAmount())
                .isAccepted(loanHistory.getIsAccepted())
                .parentId(loanHistory.getParent().getId())
                .childId(loanHistory.getChild().getId())
                .build();
    }

}
