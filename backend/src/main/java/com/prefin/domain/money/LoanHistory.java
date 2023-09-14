package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

public class LoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int loanAmount;

    private Long loanDate;

    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parents parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;

    public void lendMoney(Long loanDate) {
        this.loanDate = loanDate;
        this.isAccepted = true;
    }
    public void updateLoanRequest(int loanAmount) {
        this.loanAmount = loanAmount;
    }
}
