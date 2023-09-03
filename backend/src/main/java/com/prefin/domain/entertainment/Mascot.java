package com.prefin.domain.entertainment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Mascot {
    @Id
    @GeneratedValue
    Long id;

    String name;

    int level;

    String image;
}
