package com.prefin.dto.user;

import lombok.*;

import java.math.BigDecimal;

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

}
