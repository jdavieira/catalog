package com.csw.catalog.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Generated
@Entity
@Table(name = "genre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books;
}
