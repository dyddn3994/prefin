package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoanHistory {
    @Id
    @GeneratedValue
    Long id;

    int loanAmount;

    Long loanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    Child child;
}
