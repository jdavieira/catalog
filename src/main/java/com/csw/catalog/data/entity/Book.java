package com.csw.catalog.data.entity;

import com.csw.catalog.data.entity.enums.BookAvailability;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.Instant;
import java.util.Set;

@Generated
@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "Original_Title", nullable = false)
    private String originalTitle;

    @Column(name = "ISBN", nullable = false)
    private String isbn;

    @Column(name = "Edition", nullable = false)
    private String edition;

    @Column(name = "Synopsis", nullable = false)
    private String synopsis;

    @Column(name = "Is_Series", nullable = false)
    private boolean isSeries;

    @Column(name = "Availability", nullable = false)
    private BookAvailability availability;

    @Column(name = "Release_Date", nullable = false)
    private Date releaseDate;

    @Column(name = "Edition_Date", nullable = false)
    private Date editionDate;

    @Column(name = "Price", nullable = false)
    private Float price;

    @Column(name = "Promotional_Price", nullable = false)
    private Float promotionalPrice;

    @Column(name = "Stock_Available", nullable = false)
    private int stockAvailable;

    @Column(name = "Created_On", nullable = false)
    @CreationTimestamp
    @Setter(AccessLevel.PROTECTED)
    private Instant createdOn;

    @Column(name = "Updated_On")
    @Setter(AccessLevel.PROTECTED)
    @UpdateTimestamp
    private Instant updatedOn;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookauthor", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booklanguage", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookgenre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booktag", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookformat", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "format_id"))
    private Set<Format> formats;

    @ManyToOne(targetEntity = Publisher.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
}
