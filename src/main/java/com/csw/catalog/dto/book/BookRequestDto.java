package com.csw.catalog.dto.book;

import com.csw.catalog.dto.author.AuthorRequestDto;
import com.csw.catalog.dto.format.FormatRequestDto;
import com.csw.catalog.dto.genre.GenreRequestDto;
import com.csw.catalog.dto.language.LanguageRequestDto;
import com.csw.catalog.dto.publisher.PublisherRequestDto;
import com.csw.catalog.dto.tag.TagRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

import java.sql.Date;
import java.util.List;

@Generated
public class BookRequestDto {
    @NotNull
    @Size(max = 100)
    public String title;
    @NotNull
    @Size(max = 100)
    public String originalTitle;

    @NotNull
    @Size(max = 30)
    public String isbn;

    @NotNull
    @Size(max = 100)
    public String edition;

    @NotNull
    @Size(max = 100)
    public String synopsis;

    @NotNull
    public boolean isSeries;

    @NotNull
    public BookAvailabilityDto availability;

    @NotNull
    public Date releaseDate;

    @NotNull
    public Date editionDate;

    @NotNull
    @Min(1)
    public Float price;

    @NotNull
    public Float promotionalPrice;

    @NotNull
    @Min(0)
    public int stockAvailable;

    @Valid
    public List<AuthorRequestDto> authors;

    @Valid
    public List<LanguageRequestDto> languages;

    @Valid
    public List<GenreRequestDto> genres;

    @Valid
    public List<TagRequestDto> tags;

    @Valid
    public List<FormatRequestDto> formats;

    @Valid
    public PublisherRequestDto publisher;
}
