package com.csw.catalog.data.repository;

import com.csw.catalog.data.entity.*;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.instancio.Select.all;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Transactional
class BookRepositoryTests {


    @Inject
    private BookRepository repository;

    private Book book;

    @BeforeEach
    void setUp() {
        // Arrange
        book = createBook();
    }


    @Test
    @DisplayName("JUnit test for save Book operation")
    void givenValidBookObject_whenSave_thenReturnSaveBook() {
        // Act
        repository.persist(book);

        var bookId = book.getId();
        // Assert
        assertTrue(bookId > 0);
    }

    @Test
    @DisplayName("JUnit test for get Book List")
    void givenBookList_whenFindAll_thenReturnBookList() {
        // Arrange
        repository.persist(createBook());
        repository.persist(createBook());

        // Act
        var booksResult = repository.findAll();

        // Assert
        var books = booksResult.stream().toList();

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("JUnit test for get Book By Id")
    void givenBookObject_whenFindById_thenReturnBookObject() {
        // Arrange
        repository.persist(book);
        // Act
        var result = repository.findById(book.getId());
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("JUnit test for get Book By ISBN")
    void givenBookObject_whenFindByIsbn_thenReturnBookObject() {
        // Arrange
        repository.persist(book);
        // Act
        var result = repository.findByIsbn(book.getIsbn());
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("JUnit test for get Book By Original title")
    void givenBookObject_whenFindByOriginalTitle_thenReturnBookObject() {
        // Arrange
        repository.persist(book);
        // Act
        var result = repository.findByOriginalTitle(book.getOriginalTitle());
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("JUnit test for get Book By Synopsis")
    void givenBookObject_whenFindBySynopsis_thenReturnBookObject() {
        // Arrange
        repository.persist(book);

        var synopsis = book.getSynopsis().substring(0, 1);

        // Act
        var result = repository.findBySynopsis(synopsis);
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("JUnit test for get Book By title")
    void givenBookObject_whenFindByTitle_thenReturnBookObject() {
        // Arrange
        repository.persist(book);
        // Act
        var result = repository.findByTitle(book.getTitle());
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("JUnit test for get Book available")
    void givenBookObject_whenFindBooksAvailable_thenReturnBookObject() {
        // Arrange
        repository.persist(book);
        // Act
        var result = repository.findBooksAvailable();
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("JUnit test to delete a book by uid")
    void givenValidId_whenDeletingBook_thenShouldDeleteBook() {
        // Arrange
        repository.persist(book);

        var result = repository.findById(book.getId());

        // Act && Assert
        repository.delete(result);
    }

    private Book createBook() {
        var newBook = Instancio.of(Book.class)
                .ignore(all(field("id")))
                .ignore(all(field("publisher")))
                .ignore(all(field("authors")))
                .ignore(all(field("languages")))
                .ignore(all(field("genres")))
                .ignore(all(field("tags")))
                .ignore(all(field("formats")))
                .create();

        newBook.setStockAvailable(10);

        newBook.setPublisher(Instancio.of(Publisher.class).ignore(all(field("id"))).create());

        var genres = new HashSet<Genre>();
        genres.add(Instancio.of(Genre.class).ignore(all(field("id"))).create());
        genres.add(Instancio.of(Genre.class).ignore(all(field("id"))).create());
        genres.add(Instancio.of(Genre.class).ignore(all(field("id"))).create());

        newBook.setGenres(genres);

        var languages = new HashSet<Language>();
        languages.add(Instancio.of(Language.class).ignore(all(field("id"))).create());
        languages.add(Instancio.of(Language.class).ignore(all(field("id"))).create());
        languages.add(Instancio.of(Language.class).ignore(all(field("id"))).create());

        newBook.setLanguages(languages);

        var authors = new HashSet<Author>();
        authors.add(Instancio.of(Author.class).ignore(all(field("id"))).create());
        authors.add(Instancio.of(Author.class).ignore(all(field("id"))).create());
        authors.add(Instancio.of(Author.class).ignore(all(field("id"))).create());

        newBook.setAuthors(authors);

        var formats = new HashSet<Format>();
        formats.add(Instancio.of(Format.class).ignore(all(field("id"))).create());
        formats.add(Instancio.of(Format.class).ignore(all(field("id"))).create());
        formats.add(Instancio.of(Format.class).ignore(all(field("id"))).create());

        newBook.setFormats(formats);

        var tags = new HashSet<Tag>();
        tags.add(Instancio.of(Tag.class).ignore(all(field("id"))).create());
        tags.add(Instancio.of(Tag.class).ignore(all(field("id"))).create());
        tags.add(Instancio.of(Tag.class).ignore(all(field("id"))).create());

        newBook.setTags(tags);
        return newBook;
    }
}
