package com.csw.catalog.dto.genre;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDto {
    public int id;

    public String name;
}
