package com.prefin.dto.user;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import lombok.*;
import org.hibernate.annotations.Parent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentDto {

    private Long id;

    private String userId;

    private String password;

    private String name;

    private String fcmToken;

    private String account;

    private String simplePass;

    private BigDecimal loanRate;

    private BigDecimal savingRate;

    private int balance;

    private List<ChildDto> childDtoList;

    public static ParentDto fromEntity(Parents parent) {
        List<Child> childEntities = parent.getChildList();
        List<ChildDto> childDtos = new ArrayList<>();

        if (childEntities != null) {
            for (Child child : childEntities) {
                childDtos.add(ChildDto.fromEntity(child));
            }
        }

        return ParentDto.builder()
                .id(parent.getId())
                .userId(parent.getUserId())
                .password(parent.getPassword())
                .name(parent.getName())
                .fcmToken(parent.getFcmToken())
                .account(parent.getAccount())
                .simplePass(parent.getSimplePass())
                .loanRate(parent.getLoanRate())
                .savingRate(parent.getSavingRate())
                .balance(parent.getBalance())
                .childDtoList(childDtos)
                .build();
    }
}
