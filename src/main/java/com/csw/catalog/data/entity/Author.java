package com.csw.catalog.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    private String name;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @Column(name = "date_of_death")
    private Date dateOfDeath;

    @Column(name = "place_of_death")
    private String placeOfDeath;

    private String about;
}
