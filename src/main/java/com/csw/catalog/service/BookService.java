package com.csw.catalog.service;

import com.csw.catalog.data.entity.Book;
import com.csw.catalog.data.repository.BookRepository;
import com.csw.catalog.dto.book.BookDto;
import com.csw.catalog.dto.book.BookRequestDto;
import com.csw.catalog.mapper.BookMapper;
import com.csw.catalog.util.exception.EntityNullException;
import com.csw.catalog.util.exception.SaveEntityException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.jbosslog.JBossLog;
import org.jobrunr.scheduling.JobScheduler;

import java.util.List;

@ApplicationScoped
@JBossLog
public class BookService {

    public static final String BOOK_NOT_FOUND_FOR_ID = "Book not found for id - ";

    private final BookRepository repository;

    private final JobScheduler jobScheduler;

    private final BookMapper bookMapper;

    public BookService(BookRepository repository, JobScheduler jobScheduler, BookMapper bookMapper) {

        this.repository = repository;
        this.jobScheduler = jobScheduler;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> findAllBooks() {

        var books = this.repository.findAll().stream().toList();
        return this.bookMapper.mapBooksToBooksDto(books);
    }

    public List<BookDto> findBooksAvailable() {
        var books = this.repository.findBooksAvailable();
        return this.bookMapper.mapBooksToBooksDto(books);
    }

    public BookDto findBookById(long id) {

        var book = this.repository.findById(id);
        if (book == null) {
            var message = BOOK_NOT_FOUND_FOR_ID + id;
            log.warn(message);
            throw new NotFoundException(message);
        }
        return this.bookMapper.mapBookToBookDto(book);
    }

    public List<BookDto> findBookByTitle(String title) {

        var books = this.repository.findByTitle(title);

        if (null == books || books.isEmpty()) {
            var message = "Book not found with the title: " + title;
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return this.bookMapper.mapBooksToBooksDto(books);
    }

    public List<BookDto> findBySynopsis(String synopsis) {
        var books = this.repository.findBySynopsis(synopsis);

        if (null == books || books.isEmpty()) {
            var message = "Book not found with the synopsis: " + synopsis;
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return this.bookMapper.mapBooksToBooksDto(books);
    }

    public BookDto findByIsbn(String isbn) {
        var book = this.repository.findByIsbn(isbn).orElse(null);

        if (null == book) {
            var message = "Book not found with the isbn: " + isbn;
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return this.bookMapper.mapBookToBookDto(book);
    }

    public List<BookDto> findBookByOriginalTitle(String originalTitle) {

        var books = this.repository.findByOriginalTitle(originalTitle);

        if (null == books || books.isEmpty()) {
            var message = "Book not found with the original title: " + originalTitle;
            log.warn(message);
            throw new EntityNotFoundException(message);
        }

        return this.bookMapper.mapBooksToBooksDto(books);
    }


    @Transactional
    public void deleteBookById(long id) {

        var book = this.repository.findById(id);
        if (book == null) {
            var message = BOOK_NOT_FOUND_FOR_ID + id;
            log.warn(message);
            throw new NotFoundException(message);
        }
        this.repository.delete(book);
        log.info("Book deleted with success.");
    }

    @Transactional
    public long createBook(BookRequestDto bookRequest) {

        var book = this.bookMapper.mapBookRequestDtoToBook(bookRequest);
        if (null == book) {
            log.warn("Book Information received is null.");
            throw new EntityNullException("Book received is null");
        }
        try {
            return this.saveBook(book);
        } catch (Exception exception) {
            jobScheduler.enqueue(() -> this.saveBook(book));
            throw new SaveEntityException(exception.getMessage());
        }
    }

    @Transactional
    public void updateBookStock(long bookId, int stock) {

        var book = this.repository.findById(bookId);
        if (book == null) {
            var message = BOOK_NOT_FOUND_FOR_ID + bookId;
            log.warn(message);
            throw new NotFoundException(message);
        }
        try {
            book.setStockAvailable(book.getStockAvailable() + stock);
            this.repository.persist(book);
            log.info("Book stock updated with success.");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @Transactional
    public void updateBook(BookRequestDto bookRequestDto) {

    }

    private long saveBook(Book book) {

        try {
            this.repository.persist(book);
            log.info("Book saved with success.");
            return book.getId();
        } catch (Exception exception) {
            log.error("Error occurred while saving the book information", exception);
            throw exception;
        }
    }
}
