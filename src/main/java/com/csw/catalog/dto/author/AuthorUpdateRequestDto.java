package com.csw.catalog.dto.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorUpdateRequestDto {

    @NotNull
    public long id;

    @Size(max = 255)
    public String name;

    @Size(max = 500)
    public String originalName;

    public Date dateOfBirth;

    @Size(max = 255)
    public String placeOfBirth;

    public Date dateOfDeath;

    @Size(max = 255)
    public String placeOfDeath;

    @Size(max = 500)
    public String about;
}
