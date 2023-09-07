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
    private Long id;

    private String name;

    private int level;

    private String image;
}
