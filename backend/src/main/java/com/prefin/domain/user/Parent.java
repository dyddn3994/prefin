package com.prefin.domain.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue
    Long id;

    String email;

    String name;

    String fcmToken;

    String account;

    BigDecimal loanRate;

    BigDecimal savingRate;

}
