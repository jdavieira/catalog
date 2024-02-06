package com.csw.catalog.controller;

import com.csw.catalog.dto.book.BookDto;
import com.csw.catalog.dto.book.BookRequestDto;
import com.csw.catalog.dto.book.BookSellDto;
import com.csw.catalog.dto.book.BookUpdateRequestDto;
import com.csw.catalog.service.BookService;
import com.csw.catalog.util.exception.SaveEntityException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
@Transactional
class BookControllerTests {
    private BookService service;

    private BookController controller;

    @BeforeEach
    void setUp() {
        service = mock(BookService.class);
        controller = new BookController(this.service);
    }


    @Test
    void givenRequestForBooks_whenBooksExist_thenReturnsListOfBooks() {
        // Arrange
        var books = Instancio.ofList(BookDto.class).size(10).create();
        when(this.service.findAllBooks()).thenReturn(books);
        // Act
        var result = this.controller.getAllBooks();
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (List<BookDto>) result.getEntity();

        Assertions.assertFalse(response.isEmpty());
        assertEquals(books, response);
    }

    @Test
    void givenBookIdToDelete_whenDeletingBook_thenReturnNoContent() {
        // Arrange
        var bookId = 1L;
        doNothing().when(this.service).deleteBookById(bookId);
        // Act
        var result = this.controller.deleteBookById(bookId);
        // Assert
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
    }

    @Test
    void givenBookIdToDelete_whenBookDotNotExists_thenThrowException() {
        // Arrange
        var bookId = 1L;
        var errorMessage = "Entity not found";

        doThrow(new NotFoundException(errorMessage)).when(this.service).deleteBookById(bookId);
        // Act
        var result = this.controller.deleteBookById(bookId);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenBookId_whenBookExists_thenReturnBook() {
        // Arrange
        var bookId = 1L;
        var book = Instancio.create(BookDto.class);
        when(this.service.findBookById(bookId)).thenReturn(book);
        // Act
        var result = this.controller.getBookById(bookId);
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (BookDto) result.getEntity();

        Assertions.assertNotNull(response);
        assertEquals(book, response);
    }

    @Test
    void givenBookId_whenBookDotNotExists_thenThrowException() {
        // Arrange
        var errorMessage = "Entity not found";
        var bookId = 1L;
        when(this.service.findBookById(bookId)).thenThrow(new NotFoundException(errorMessage));
        // Act
        var result = this.controller.getBookById(bookId);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenValidBook_whenCreatingBook_thenReturnsBookId() {
        // Arrange
        var bookId = 1L;
        var book = Instancio.create(BookRequestDto.class);
        when(this.service.createBook(book)).thenReturn(bookId);
        // Act
        var result = this.controller.createBook(book);

        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (long) result.getEntity();
        Assertions.assertEquals(bookId, response);
    }

    @Test
    void givenBookToCreate_whenErrorOccursWhileCreating_thenThrowException() {
        // Arrange
        var errorMessage = "Error creating entity";
        var book = Instancio.create(BookRequestDto.class);
        when(this.service.createBook(book)).thenThrow(new SaveEntityException(errorMessage));
        // Act
        var result = this.controller.createBook(book);
        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenValidBook_whenUpdatingBook_thenReturnsOk() {
        // Arrange
        var bookId = 1;
        var book = Instancio.create(BookUpdateRequestDto.class);
        when(this.service.updateBook(bookId, book)).thenReturn(new CompletableFuture());
        // Act
        var result = this.controller.updateBook(bookId, book);
        // Assert
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
    }

    @Test
    void givenValidBook_whenErrorOccursWhileUpdatingBook_thenThrowsException() {
        // Arrange
        var errorMessage = "Error updating entity";
        var bookId = 1;
        var book = Instancio.create(BookUpdateRequestDto.class);
        doThrow(new SaveEntityException(errorMessage)).when(this.service).updateBook(bookId, book);
        // Act
        var result = this.controller.updateBook(bookId, book);
        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());

        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }


    @Test
    void givenValidBook_whenSellingBook_thenReturnsNoContent() {
        // Arrange
        var sellBook = Instancio.create(BookSellDto.class);
        when(this.service.sellBook(sellBook.id, sellBook.stock)).thenReturn(new CompletableFuture());
        // Act
        var result = this.controller.sellBook(sellBook);
        // Assert
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
    }

    @Test
    void givenValidBook_whenErrorOccursWhileSellingBook_thenThrowsException() {
        // Arrange
        var errorMessage = "Error selling book";
        var sellBook = Instancio.create(BookSellDto.class);
        doThrow(new SaveEntityException(errorMessage)).when(this.service).sellBook(sellBook.id, sellBook.stock);
        // Act
        var result = this.controller.sellBook(sellBook);
        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());

        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenBookId_whenBookIdDoesNotExists_thenThrowsException() {
        // Arrange
        var errorMessage = "Entity not found";
        var bookId = 1;
        var book = Instancio.create(BookUpdateRequestDto.class);
        doThrow(new NotFoundException(errorMessage)).when(this.service).updateBook(bookId, book);
        // Act
        var result = this.controller.updateBook(bookId, book);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenBookOriginalTitle_whenBookExists_thenReturnBook() {
        // Arrange
        var bookOriginalTitle = "original title";
        var books = Instancio.ofList(BookDto.class).size(10).create();
        when(this.service.findBookByOriginalTitle(bookOriginalTitle)).thenReturn(books);
        // Act
        var result = this.controller.getBooksByOriginalTitle(bookOriginalTitle);
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (List<BookDto>) result.getEntity();

        Assertions.assertFalse(response.isEmpty());
        assertEquals(books, response);
    }

    @Test
    void givenBookOriginalTitle_whenBookDotNotExists_thenThrowException() {
        // Arrange
        var errorMessage = "Entity not found";
        var bookOriginalTitle = "original title";
        when(this.service.findBookByOriginalTitle(bookOriginalTitle)).thenThrow(new NotFoundException(errorMessage));
        // Act
        var result = this.controller.getBooksByOriginalTitle(bookOriginalTitle);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenBookTitle_whenBookExists_thenReturnBook() {
        // Arrange
        var bookTitle = "title";
        var books = Instancio.ofList(BookDto.class).size(10).create();
        when(this.service.findBookByTitle(bookTitle)).thenReturn(books);
        // Act
        var result = this.controller.getBooksByTitle(bookTitle);
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (List<BookDto>) result.getEntity();

        Assertions.assertFalse(response.isEmpty());
        assertEquals(books, response);
    }

    @Test
    void givenBookTitle_whenBookDotNotExists_thenThrowException() {
        // Arrange
        var errorMessage = "Entity not found";
        var bookTitle = "title";
        when(this.service.findBookByTitle(bookTitle)).thenThrow(new NotFoundException(errorMessage));
        // Act
        var result = this.controller.getBooksByTitle(bookTitle);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenBookIsbn_whenBookExists_thenReturnBook() {
        // Arrange
        var bookIsbn = "Isbn";
        var book = Instancio.create(BookDto.class);
        when(this.service.findByIsbn(bookIsbn)).thenReturn(book);
        // Act
        var result = this.controller.getBooksByISBN(bookIsbn);
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (BookDto) result.getEntity();

        Assertions.assertNotNull(response);
        assertEquals(book, response);
    }

    @Test
    void givenBookIsbn_whenBookDotNotExists_thenThrowException() {
        // Arrange
        var errorMessage = "Entity not found";
        var bookIsbn = "Isbn";
        when(this.service.findByIsbn(bookIsbn)).thenThrow(new NotFoundException(errorMessage));
        // Act
        var result = this.controller.getBooksByISBN(bookIsbn);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenBookSynopsis_whenBookExists_thenReturnBook() {
        // Arrange
        var bookSynopsis = "synopsis";
        var books = Instancio.ofList(BookDto.class).size(10).create();
        when(this.service.findBySynopsis(bookSynopsis)).thenReturn(books);
        // Act
        var result = this.controller.getBooksBySynopsis(bookSynopsis);
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (List<BookDto>) result.getEntity();

        Assertions.assertFalse(response.isEmpty());
        assertEquals(books, response);
    }

    @Test
    void givenBookSynopsis_whenBookDotNotExists_thenThrowException() {
        // Arrange
        var errorMessage = "Entity not found";
        var bookSynopsis = "synopsis";
        when(this.service.findBySynopsis(bookSynopsis)).thenThrow(new NotFoundException(errorMessage));
        // Act
        var result = this.controller.getBooksBySynopsis(bookSynopsis);
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        var response = result.getStatusInfo();
        Assertions.assertEquals(errorMessage, response.getReasonPhrase());
    }

    @Test
    void givenRequestForBooks_whenBooksWithStockExistExist_thenReturnsListOfBooks() {
        // Arrange
        var books = Instancio.ofList(BookDto.class).size(10).create();
        when(this.service.findBooksAvailable()).thenReturn(books);
        // Act
        var result = this.controller.getBookAvailable();
        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatus());
        var response = (List<BookDto>) result.getEntity();

        Assertions.assertFalse(response.isEmpty());
        assertEquals(books, response);
    }

}
