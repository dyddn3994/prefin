package com.prefin.dto.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentSignUpDto {

    private String userId;

    private String password;

    private String name;

}
