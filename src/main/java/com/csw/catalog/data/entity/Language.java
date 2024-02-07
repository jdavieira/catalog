package com.csw.catalog.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Generated
@Entity
@Table(name = "language")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String culture;

    @ManyToMany(mappedBy = "languages")
    private Set<Book> books;
}
