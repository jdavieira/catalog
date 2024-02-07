package com.csw.catalog.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Generated
@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books;
}
