package com.csw.catalog.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Generated
@Entity
@Table(name = "format")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "formats")
    private Set<Book> books;
}
