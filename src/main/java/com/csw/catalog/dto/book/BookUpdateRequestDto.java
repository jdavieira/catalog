package com.csw.catalog.dto.book;

import com.csw.catalog.dto.author.AuthorUpdateRequestDto;
import com.csw.catalog.dto.format.FormatUpdateRequestDto;
import com.csw.catalog.dto.genre.GenreUpdateRequestDto;
import com.csw.catalog.dto.language.LanguageUpdateRequestDto;
import com.csw.catalog.dto.publisher.PublisherUpdateRequestDto;
import com.csw.catalog.dto.tag.TagUpdateRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.util.List;

public class BookUpdateRequestDto {

    @Size(max = 100)
    public String title;

    @Size(max = 100)
    public String originalTitle;

    @Size(max = 30)
    public String isbn;

    @Size(max = 100)
    public String edition;

    @Size(max = 100)
    public String synopsis;

    public Boolean isSeries;

    public BookAvailabilityDto availability;

    public Date releaseDate;

    public Date editionDate;

    @Min(1)
    public Float price;

    public Float promotionalPrice;

    @Valid
    public List<AuthorUpdateRequestDto> authors;

    @Valid
    public List<LanguageUpdateRequestDto> languages;

    @Valid
    public List<GenreUpdateRequestDto> genres;

    @Valid
    public List<TagUpdateRequestDto> tags;

    @Valid
    public List<FormatUpdateRequestDto> formats;

    @Valid
    public PublisherUpdateRequestDto publisher;
}
