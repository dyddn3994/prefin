package com.prefin.dto.money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistoryDto {
    private Long id;

    private Long childId;

    private String transactionDate;

    private String transactionTime;

    private String briefs;

    private String deposit;

    private String withdraw;
}
