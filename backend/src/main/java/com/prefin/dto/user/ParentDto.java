package com.prefin.dto.user;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentDto {

    private String userId;

    private String password;

    private String name;

}
