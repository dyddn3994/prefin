package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Allowance {
    @Id
    @GeneratedValue
    Long id;

    int allowanceAmount;

    Long payDay;

    int loanAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Parent parent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    Child child;
}
