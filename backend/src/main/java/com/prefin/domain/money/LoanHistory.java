package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanHistory {
    @Id
    @GeneratedValue
    private Long id;

    private int loanAmount;

    private Long loanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;
}
