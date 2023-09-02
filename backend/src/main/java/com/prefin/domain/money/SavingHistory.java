package com.prefin.domain.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SavingHistory {
    @Id
    @GeneratedValue
    Long id;

    int savingAmount;

    Long savingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    Child child;
}
