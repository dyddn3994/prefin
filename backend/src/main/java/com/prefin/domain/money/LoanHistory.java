package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
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
    private Long id;

    private int loanAmount;

    private Long loanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parents parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;
}
