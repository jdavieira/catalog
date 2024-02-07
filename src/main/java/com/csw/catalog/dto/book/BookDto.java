package com.csw.catalog.dto.book;

import com.csw.catalog.dto.author.AuthorDto;
import com.csw.catalog.dto.format.FormatDto;
import com.csw.catalog.dto.genre.GenreDto;
import com.csw.catalog.dto.language.LanguageDto;
import com.csw.catalog.dto.publisher.PublisherDto;
import com.csw.catalog.dto.tag.TagDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {

    public int id;

    public String title;

    public String originalTitle;

    public String isbn;

    public String edition;

    public String synopsis;

    public boolean isSeries;

    public BookAvailabilityDto availability;

    public Date releaseDate;

    public Date editionDate;

    public Float price;

    public Float promotionalPrice;

    public int stockAvailable;

    public Instant createdOn;

    public Instant updatedOn;

    public List<AuthorDto> authors;

    public List<LanguageDto> languages;

    public List<GenreDto> genres;

    public List<TagDto> tags;

    public List<FormatDto> formats;

    public PublisherDto publisher;
}
