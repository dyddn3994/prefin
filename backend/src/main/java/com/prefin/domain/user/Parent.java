package com.prefin.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
