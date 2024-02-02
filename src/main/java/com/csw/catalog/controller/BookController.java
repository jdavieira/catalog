package com.csw.catalog.controller;

import com.csw.catalog.dto.book.BookRequestDto;
import com.csw.catalog.dto.book.BookSellDto;
import com.csw.catalog.dto.book.BookUpdateRequestDto;
import com.csw.catalog.service.BookService;
import com.csw.catalog.util.exception.EntityNullException;
import com.csw.catalog.util.exception.SaveEntityException;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import java.util.concurrent.CompletableFuture;

@Path("/v1/api/book")
@ApplicationScoped
@Authenticated
@JBossLog
public class BookController {

    private final BookService service;

    public BookController(BookService service) {

        this.service = service;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllBooks() {

        return Response.ok(this.service.findAllBooks()).build();
    }

    @GET
    @Path("/available")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBookAvailable() {

        return Response.ok(this.service.findBooksAvailable()).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBookById(long id) {

        try {
            var book = this.service.findBookById(id);
            return Response.ok(book).build();
        } catch (NotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }

    @GET
    @Path("/title/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooksByTitle(String title) {

        try {
            var book = this.service.findBookByTitle(title);
            return Response.ok(book).build();
        } catch (NotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }

    @GET
    @Path("/originalTitle/{originalTitle}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooksByOriginalTitle(String originalTitle) {

        try {
            var book = this.service.findBookByOriginalTitle(originalTitle);
            return Response.ok(book).build();
        } catch (NotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }

    @GET
    @Path("/isbn/{isbn}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooksByISBN(String isbn) {

        try {
            var book = this.service.findByIsbn(isbn);
            return Response.ok(book).build();
        } catch (NotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }

    @GET
    @Path("/synopsis/{synopsis}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooksBySynopsis(String synopsis) {

        try {
            var book = this.service.findBySynopsis(synopsis);
            return Response.ok(book).build();
        } catch (NotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createBook(@Valid BookRequestDto bookRequest) {
        try {
            var bookId = this.service.createBook(bookRequest);
            return Response.ok(bookId).build();
        } catch (SaveEntityException exception) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage()).build();
        }
    }

    @POST
    @Path("/sellBook")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response sellBook(@Valid BookSellDto bookSellDto) {
        try {

            var sellFuture = this.service.sellBook(bookSellDto.id, bookSellDto.stock);
            CompletableFuture.completedFuture(sellFuture);

            return Response.noContent().build();
        } catch (Exception exception) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage()).build();
        }
    }

    @PUT
    @Path("/{bookId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBook(long bookId, @Valid BookUpdateRequestDto bookUpdateRequest) {
        try {
            var updateBook = this.service.updateBook(bookId, bookUpdateRequest);
            CompletableFuture.completedFuture(updateBook);
            return Response.noContent().build();
        } catch (SaveEntityException | EntityNullException exception) {
            log.warn(exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage()).build();
        } catch (NotFoundException exception) {
            log.warn(exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBookById(long id) {

        try {
            this.service.deleteBookById(id);
            return Response.noContent().build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        }
    }
}
