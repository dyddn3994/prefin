package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int allowanceAmount;

    private Long payday;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parents parent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;

    public void updateAllowance(int money, Long payday) {
        this.allowanceAmount = money;
        this.payday = payday;
    }
}
