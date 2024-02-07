package com.csw.catalog.util.book;

import com.csw.catalog.data.entity.*;
import com.csw.catalog.data.entity.enums.BookAvailability;
import com.csw.catalog.dto.author.AuthorUpdateRequestDto;
import com.csw.catalog.dto.book.BookAvailabilityDto;
import com.csw.catalog.dto.book.BookUpdateRequestDto;
import com.csw.catalog.dto.format.FormatUpdateRequestDto;
import com.csw.catalog.dto.genre.GenreUpdateRequestDto;
import com.csw.catalog.dto.language.LanguageUpdateRequestDto;
import com.csw.catalog.dto.publisher.PublisherUpdateRequestDto;
import com.csw.catalog.dto.tag.TagUpdateRequestDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Generated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class Validation {

    public Book updateBook(Book book, BookUpdateRequestDto bookUpdateRequestDto) {

        if (bookUpdateRequestDto.title != null) {
            book.setTitle(bookUpdateRequestDto.title);
        }

        if (bookUpdateRequestDto.originalTitle != null) {
            book.setOriginalTitle(bookUpdateRequestDto.originalTitle);
        }

        if (bookUpdateRequestDto.availability != null) {

            book.setAvailability(mapAvailability(bookUpdateRequestDto.availability));
        }

        if (bookUpdateRequestDto.edition != null) {
            book.setEdition(bookUpdateRequestDto.edition);
        }

        if (bookUpdateRequestDto.isbn != null) {
            book.setIsbn(bookUpdateRequestDto.isbn);
        }

        if (bookUpdateRequestDto.editionDate != null) {
            book.setEditionDate(bookUpdateRequestDto.editionDate);
        }
        if (bookUpdateRequestDto.releaseDate != null) {
            book.setReleaseDate(bookUpdateRequestDto.releaseDate);
        }
        if (bookUpdateRequestDto.synopsis != null) {
            book.setSynopsis(bookUpdateRequestDto.synopsis);
        }

        if (bookUpdateRequestDto.isSeries != null) {
            book.setSeries(bookUpdateRequestDto.isSeries);
        }

        if (bookUpdateRequestDto.price != null) {
            book.setPrice(bookUpdateRequestDto.price);
        }

        if (bookUpdateRequestDto.promotionalPrice != null) {
            book.setPromotionalPrice(bookUpdateRequestDto.promotionalPrice);
        }

        mapProperties(book, bookUpdateRequestDto);

        return book;
    }

    private static void mapProperties(Book book, BookUpdateRequestDto bookUpdateRequestDto) {
        if (bookUpdateRequestDto.authors != null) {
            book.setAuthors(mapAuthors(book.getAuthors(), bookUpdateRequestDto.authors));
        }

        if (bookUpdateRequestDto.languages != null) {
            book.setLanguages(mapLanguages(book.getLanguages(), bookUpdateRequestDto.languages));
        }

        if (bookUpdateRequestDto.genres != null) {
            book.setGenres(mapGenres(book.getGenres(), bookUpdateRequestDto.genres));
        }

        if (bookUpdateRequestDto.tags != null) {
            book.setTags(mapTags(book.getTags(), bookUpdateRequestDto.tags));
        }

        if (bookUpdateRequestDto.formats != null) {
            book.setFormats(mapFormats(book.getFormats(), bookUpdateRequestDto.formats));
        }

        if (bookUpdateRequestDto.publisher != null) {
            book.setPublisher(mapPublisher(book.getPublisher(), bookUpdateRequestDto.publisher));
        }
    }

    private static Set<Genre> mapGenres(Set<Genre> genres, List<GenreUpdateRequestDto> genresDto) {
        if (genres.stream().noneMatch(x -> genresDto.stream().anyMatch(s -> s.id == x.getId()))) {
            return genres;
        }

        var updatedGenres = new HashSet<Genre>();

        for (var gender : genres) {
            var genderDto = genresDto.stream().filter(s -> s.id == gender.getId()).findFirst();

            genderDto.ifPresent(genreUpdateRequestDto -> gender.setName(genreUpdateRequestDto.name));
            updatedGenres.add(gender);
        }

        return updatedGenres;
    }

    private static Set<Tag> mapTags(Set<Tag> tags, List<TagUpdateRequestDto> tagsDto) {
        if (tags.stream().noneMatch(x -> tagsDto.stream().anyMatch(s -> s.id == x.getId()))) {
            return tags;
        }
        var updatedTags = new HashSet<Tag>();

        for (var tag : tags) {
            var tagDto = tagsDto.stream().filter(s -> s.id == tag.getId()).findFirst();

            tagDto.ifPresent(tagUpdateRequestDto -> tag.setName(tagUpdateRequestDto.name));

            updatedTags.add(tag);
        }

        return updatedTags;
    }

    private static Set<Format> mapFormats(Set<Format> formats, List<FormatUpdateRequestDto> formatsDto) {
        if (formats.stream().noneMatch(x -> formatsDto.stream().anyMatch(s -> s.id == x.getId()))) {
            return formats;
        }
        var updatedFormats = new HashSet<Format>();

        for (var format : formats) {
            var formatDto = formatsDto.stream().filter(s -> s.id == format.getId()).findFirst();

            formatDto.ifPresent(formatUpdateRequestDto -> format.setName(formatUpdateRequestDto.name));

            updatedFormats.add(format);
        }

        return updatedFormats;
    }

    private static Set<Language> mapLanguages(Set<Language> languages, List<LanguageUpdateRequestDto> languagesDto) {
        if (languages.stream().noneMatch(x -> languagesDto.stream().anyMatch(s -> s.id == x.getId()))) {
            return languages;
        }
        var updatedLanguages = new HashSet<Language>();

        for (var language : languages) {
            var languageDto = languagesDto.stream().filter(s -> s.id == language.getId()).findFirst();

            if (languageDto.isPresent()) {

                if (languageDto.get().name != null) {
                    language.setName(languageDto.get().name);
                }
                if (languageDto.get().culture != null) {
                    language.setCulture(languageDto.get().culture);
                }
            }
            updatedLanguages.add(language);
        }

        return updatedLanguages;
    }

    private static Set<Author> mapAuthors(Set<Author> authors, List<AuthorUpdateRequestDto> authorsDto) {
        var authorExists = authors.stream().noneMatch(x -> authorsDto.stream().anyMatch(s -> s.id == x.getId()));
        if (authorExists) {
            return authors;
        }
        var updatedAuthors = new HashSet<Author>();

        for (var author : authors) {
            var authorDto = authorsDto.stream().filter(s -> s.id == author.getId()).findFirst();

            authorDto.ifPresent(authorUpdateRequestDto -> updateAuthor(author, authorUpdateRequestDto));

            updatedAuthors.add(author);
        }

        return updatedAuthors;

    }

    private static Author updateAuthor(Author author, AuthorUpdateRequestDto authorDto) {

        if (authorDto.name != null) {
            author.setName(authorDto.name);
        }
        if (authorDto.originalName != null) {
            author.setOriginalName(authorDto.originalName);
        }
        if (authorDto.about != null) {
            author.setAbout(authorDto.about);
        }
        if (authorDto.dateOfBirth != null) {
            author.setDateOfBirth(authorDto.dateOfBirth);
        }
        if (authorDto.placeOfBirth != null) {
            author.setPlaceOfBirth(authorDto.placeOfBirth);
        }
        if (authorDto.dateOfDeath != null) {
            author.setDateOfDeath(authorDto.dateOfDeath);
        }
        if (authorDto.placeOfDeath != null) {
            author.setPlaceOfDeath(authorDto.placeOfDeath);
        }

        return author;
    }

    private static Publisher mapPublisher(Publisher publisher, PublisherUpdateRequestDto publisherDto) {
        if (publisher.getId() == publisherDto.id) {
            publisher.setName(publisherDto.name);
        }

        return publisher;
    }

    @Generated
    private static BookAvailability mapAvailability(BookAvailabilityDto availability) {
        return switch (availability) {
            case ON_PRE_ORDER -> BookAvailability.ON_PRE_ORDER;
            case ON_ORDER -> BookAvailability.ON_ORDER;
            case AVAILABLE -> BookAvailability.AVAILABLE;
            default -> BookAvailability.TO_BE_LAUNCHED;
        };
    }
}
