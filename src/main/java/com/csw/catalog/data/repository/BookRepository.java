package com.csw.catalog.data.repository;

import com.csw.catalog.data.entity.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    public List<Book> findByTitle(String title) {
        return find("title", title).stream().toList();
    }

    public List<Book> findByOriginalTitle(String originalTitle) {
        return find("originalTitle", originalTitle).stream().toList();
    }

    public Optional<Book> findByIsbn(String isbn) {
        return find("isbn", isbn).firstResultOptional();
    }

    public List<Book> findBooksAvailable() {
        return find("stockAvailable > 0").stream().toList();
    }

    public List<Book> findBySynopsis(String synopsis) {

        var searchSynopsis = "%" + synopsis + "%";
        return find("synopsis LIKE ?1", searchSynopsis).stream().toList();
    }
}
