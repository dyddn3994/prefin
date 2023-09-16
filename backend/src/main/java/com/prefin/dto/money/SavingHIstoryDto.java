package com.prefin.dto.money;

import com.prefin.domain.money.SavingHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingHIstoryDto {
    private Long id;

    private int savingAmount;
    private Long savingDate;

    private Long parentId;
    private Long childId;

    private String savingType;

    public static SavingHIstoryDto fromEntity(SavingHistory savingHistory) {
        return SavingHIstoryDto.builder()
                .id(savingHistory.getId())
                .savingAmount(savingHistory.getSavingAmount())
                .savingDate(savingHistory.getSavingDate())
                .parentId(savingHistory.getParent().getId())
                .childId(savingHistory.getChild().getId())
                .savingType(savingHistory.getSavingType())
                .build();
    }


}
