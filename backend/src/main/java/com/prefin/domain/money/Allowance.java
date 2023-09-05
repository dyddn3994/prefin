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
    private Long id;

    private int allowanceAmount;

    private Long payDay;

    private int loanAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;
}
