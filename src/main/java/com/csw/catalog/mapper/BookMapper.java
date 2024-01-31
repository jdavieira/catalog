package com.csw.catalog.mapper;

import com.csw.catalog.data.entity.Book;
import com.csw.catalog.dto.book.BookDto;
import com.csw.catalog.dto.book.BookRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface BookMapper {

    BookDto mapBookToBookDto(Book book);

    List<BookDto> mapBooksToBooksDto(List<Book> books);

    Book mapBookRequestDtoToBook(BookRequestDto bookRequest);
}