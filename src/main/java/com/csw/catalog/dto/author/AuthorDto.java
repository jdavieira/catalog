package com.csw.catalog.dto.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;

import java.sql.Date;

@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDto {
    public int id;

    public String name;

    public String originalName;

    public Date dateOfBirth;

    public String placeOfBirth;
    public Date dateOfDeath;

    public String placeOfDeath;

    public String about;
}
