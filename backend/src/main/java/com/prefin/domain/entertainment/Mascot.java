package com.prefin.domain.entertainment;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Mascot {
    @Id
    @GeneratedValue
    Long id;

    String name;

    int level;

    String image;
}
