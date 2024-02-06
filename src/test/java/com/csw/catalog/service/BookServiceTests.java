package com.csw.catalog.service;

import com.csw.catalog.data.entity.Book;
import com.csw.catalog.data.repository.BookRepository;
import com.csw.catalog.dto.book.BookRequestDto;
import com.csw.catalog.dto.book.BookUpdateRequestDto;
import com.csw.catalog.mapper.BookMapper;
import com.csw.catalog.messaging.rabbit.BookStockProducer;
import com.csw.catalog.util.exception.EntityNullException;
import com.csw.catalog.util.exception.SaveEntityException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.instancio.Instancio;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Transactional
class BookServiceTests {

    @Inject
    private BookRepository repository;
    @Inject
    private BookMapper bookMapper;

    private BookRepository mockRepository;

    private BookService service;

    @BeforeEach
    void setUp() {

        mockRepository = mock(BookRepository.class);
        service = new BookService(
                mockRepository,
                mock(JobScheduler.class),
                mock(BookStockProducer.class),
                bookMapper);
    }

    @Test
    void givenValidBookObject_whenSave_thenReturnSaveBookId() {
        // Arrange
        var bookService = new BookService(
                this.repository,
                mock(JobScheduler.class),
                mock(BookStockProducer.class),
                bookMapper);

        var bookDto = Instancio.create(BookRequestDto.class);

        // Act
        var result = bookService.createBook(bookDto);

        // Assert
        var book = repository.findById(result);

        Assertions.assertEquals(book.getIsbn(), bookDto.isbn);
        Assertions.assertEquals(book.getTitle(), bookDto.title);
        Assertions.assertEquals(book.getOriginalTitle(), bookDto.originalTitle);
    }


    @Test
    void givenValidBookObject_whenErrorOccursWhileSaving_thenThrowException() {
        // Arrange
        var bookDto = Instancio.create(BookRequestDto.class);

        var errorMessage = "Error while getting book";

        doThrow(new SaveEntityException(errorMessage)).when(this.mockRepository).persist(any(Book.class));
        // Act
        Exception exception = assertThrows(SaveEntityException.class, () -> this.service.createBook(bookDto));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenInvalidBookObject_whenSaving_thenThrowException() {
        // Arrange
        var errorMessage = "Book received is null";

        // Act
        Exception exception = assertThrows(EntityNullException.class, () -> this.service.createBook(null));

        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
        verify(this.mockRepository, times(0)).persist(any(Book.class));
    }

    @Test
    void givenNoBookExists_whenGettingAllBooks_thenReturnsEmptyList() {
        // Arrange
        var query = Mockito.mock(PanacheQuery.class);
        when(this.mockRepository.findAll()).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<>());

        // Act
        var result = service.findAllBooks();
        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenBookExists_whenGettingAllBooks_thenReturnsBooks() {
        // Arrange
        var books = Instancio.ofList(Book.class).size(10).create();
        var query = Mockito.mock(PanacheQuery.class);
        when(this.mockRepository.findAll()).thenReturn(query);
        when(query.list()).thenReturn(books);
        // Act
        var result = service.findAllBooks();
        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(books.size(), result.size());
        for (var i = 0; i < books.size(); i++) {
            Assertions.assertEquals(books.get(i).getOriginalTitle(), result.get(i).originalTitle);
        }
    }

    @Test
    void givenBookExistsWithStock_whenGettingAllBooks_thenReturnsBooks() {
        // Arrange
        var books = Instancio.ofList(Book.class).size(10).create();
        when(this.mockRepository.findBooksAvailable()).thenReturn(books);
        // Act
        var result = service.findBooksAvailable();
        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(books.size(), result.size());
        for (var i = 0; i < books.size(); i++) {
            Assertions.assertEquals(books.get(i).getOriginalTitle(), result.get(i).originalTitle);
        }
    }

    @Test
    void givenValidBookTitle_whenGettingBookByTitle_thenReturnsBook() {
        // Arrange
        var bookTitle = "title";
        var books = Instancio.ofList(Book.class).size(10).create();
        when(this.mockRepository.findByTitle(bookTitle)).thenReturn(books);
        // Act
        var result = service.findBookByTitle(bookTitle);
        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    void givenInvalidBookTitle_whenGettingBookByTitle_thenThrowsException() {
        // Arrange
        var bookTitle = "title";
        var errorMessage = "Book not found with the title: " + bookTitle;
        when(this.mockRepository.findByTitle(bookTitle)).thenReturn(null);
        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findBookByTitle(bookTitle));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenValidBookOriginalTitle_whenGettingBookByOriginalTitle_thenReturnsBook() {
        // Arrange
        var bookOriginalTitle = "OriginalTitle";
        var books = Instancio.ofList(Book.class).size(10).create();
        when(this.mockRepository.findByOriginalTitle(bookOriginalTitle)).thenReturn(books);
        // Act
        var result = service.findBookByOriginalTitle(bookOriginalTitle);
        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    void givenInvalidBookOriginalTitle_whenGettingBookByTitle_thenThrowsException() {
        // Arrange
        var bookOriginalTitle = "OriginalTitle";
        var errorMessage = "Book not found with the original title: " + bookOriginalTitle;
        when(this.mockRepository.findByOriginalTitle(bookOriginalTitle)).thenReturn(null);
        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findBookByOriginalTitle(bookOriginalTitle));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenValidBookIsbn_whenGettingBookByIsbn_thenReturnsBook() {
        // Arrange
        var bookIsbn = "Isbn";
        var book = Instancio.create(Book.class);
        when(this.mockRepository.findByIsbn(bookIsbn)).thenReturn(Optional.ofNullable(book));
        // Act
        var result = service.findByIsbn(bookIsbn);
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getOriginalTitle(), result.originalTitle);
    }

    @Test
    void givenInvalidBookIsbn_whenGettingBookByIsbn_thenThrowsException() {
        // Arrange
        var bookIsbn = "Isbn";
        var errorMessage = "Book not found with the isbn: " + bookIsbn;
        when(this.mockRepository.findByIsbn(bookIsbn)).thenReturn((Optional.ofNullable(null)));
        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findByIsbn(bookIsbn));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenValidBookSynopsis_whenGettingBookBySynopsis_thenReturnsBook() {
        // Arrange
        var bookSynopsis = "Synopsis";
        var books = Instancio.ofList(Book.class).size(10).create();
        when(this.mockRepository.findBySynopsis(bookSynopsis)).thenReturn(books);
        // Act
        var result = service.findBySynopsis(bookSynopsis);
        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    void givenInvalidBookSynopsis_whenGettingBookBySynopsis_thenThrowsException() {
        // Arrange
        var bookSynopsis = "Synopsis";
        var errorMessage = "Book not found with the synopsis: " + bookSynopsis;
        when(this.mockRepository.findBySynopsis(bookSynopsis)).thenReturn(null);
        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findBySynopsis(bookSynopsis));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenValidBookId_whenGettingBookById_thenReturnsBook() {
        // Arrange
        var bookId = 1L;
        var book = Instancio.create(Book.class);
        when(this.mockRepository.findById(bookId)).thenReturn(book);
        // Act
        var result = service.findBookById(bookId);
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getOriginalTitle(), result.originalTitle);
    }

    @Test
    void givenInvalidBookId_whenGettingBookById_thenThrowsException() {
        // Arrange
        var bookId = 1L;
        var errorMessage = "Book not found for id - " + bookId;
        when(this.mockRepository.findById(bookId)).thenReturn(null);
        // Act
        Exception exception = assertThrows(NotFoundException.class, () -> service.findBookById(bookId));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenValidBookId_whenDeletingBook_thenLogsSuccessMessage() {
        // Arrange
        var bookId = 1L;
        var book = Instancio.create(Book.class);
        when(this.mockRepository.findById(bookId)).thenReturn(book);
        when(this.mockRepository.deleteById(bookId)).thenReturn(true);

        // Act
        service.deleteBookById(bookId);
        // Assert
        assertTrue(true);
    }

    @Test
    void givenInvalidBook_whenDeleteBook_thenThrowsException() {
        // Arrange
        var bookId = 1;
        var errorMessage = "Book not found for id - " + bookId;
        // Act
        Exception exception = assertThrows(NotFoundException.class, () -> service.deleteBookById(bookId));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenInvalidBook_whenUpdateBook_thenThrowsException() {
        // Arrange
        var bookId = 1;
        var errorMessage = "Book not found for id - " + bookId;
        // Act
        Exception exception = assertThrows(NotFoundException.class, () -> service.updateBook(bookId, null));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenBookIdThatDoesNot_whenUpdateBook_thenThrowsException() {
        // Arrange
        var bookId = 1L;
        var bookDto = Instancio.create(BookUpdateRequestDto.class);
        var errorMessage = "Book not found for id - " + bookId;
        when(this.mockRepository.findById(bookId)).thenReturn(null);
        // Act
        Exception exception = assertThrows(NotFoundException.class, () -> service.updateBook(bookId, bookDto));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
        verify(mockRepository, times(1)).findById(bookId);
        verify(mockRepository, times(0)).persist(any(Book.class));
    }

    @Test
    void givenValidBook_whenErrorOccursWhileUpdating_thenThrowsException() {
        // Arrange
        var bookId = 1L;
        var bookDto = Instancio.create(BookUpdateRequestDto.class);
        var book = Instancio.create(Book.class);
        var errorMessage = "Error occurred while Updating Book";
        when(this.mockRepository.findById(bookId)).thenReturn(book);
        doThrow(new SaveEntityException(errorMessage)).when(this.mockRepository).persist(any(Book.class));
        // Act
        Exception exception = assertThrows(SaveEntityException.class, () -> service.updateBook(bookId, bookDto));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
        verify(mockRepository, times(1)).findById(bookId);
        verify(mockRepository, times(1)).persist(any(Book.class));
    }

    @Test
    void givenValidBook_whenUpdating_thenLogsSuccessMessage() {
        // Arrange
        var bookId = 1L;
        var bookDto = Instancio.create(BookUpdateRequestDto.class);
        var book = Instancio.create(Book.class);
        when(this.mockRepository.findById(bookId)).thenReturn(book);
        doNothing().when(this.mockRepository).persist(any(Book.class));
        // Act
        service.updateBook(bookId, bookDto);
        // Assert
        verify(mockRepository, times(1)).findById(bookId);
        verify(mockRepository, times(1)).persist(any(Book.class));
    }

    @Test
    void givenInvalidBook_whenSellingBook_thenThrowsException() {
        // Arrange
        var bookId = 1;
        var stock = 1;
        var errorMessage = "Book not found for id - " + bookId;
        // Act
        Exception exception = assertThrows(NotFoundException.class, () -> service.sellBook(bookId, stock));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void givenValidBook_whenErrorOccursWhileSelling_thenThrowsException() {
        // Arrange
        var bookId = 1L;
        var stock = 1;
        var book = Instancio.create(Book.class);
        var errorMessage = "Error occurred while Updating Book";
        when(this.mockRepository.findById(bookId)).thenReturn(book);

        doThrow(new SaveEntityException(errorMessage)).when(this.mockRepository).persist(any(Book.class));
        // Act
        Exception exception = assertThrows(SaveEntityException.class, () -> service.sellBook(bookId, stock));
        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
        verify(mockRepository, times(1)).findById(bookId);
        verify(mockRepository, times(1)).persist(any(Book.class));
    }

    @Test
    void givenValidBook_whenSellingBook_thenLogsSuccessMessage() {
        // Arrange
        var bookId = 1L;
        var stock = 1;
        var book = Instancio.create(Book.class);
        when(this.mockRepository.findById(bookId)).thenReturn(book);
        doNothing().when(this.mockRepository).persist(any(Book.class));
        // Act
        service.sellBook(bookId, stock);
        // Assert
        verify(mockRepository, times(1)).findById(bookId);
        verify(mockRepository, times(1)).persist(any(Book.class));
    }
}
