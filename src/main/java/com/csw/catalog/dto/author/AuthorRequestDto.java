package com.csw.catalog.dto.author;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

import java.sql.Date;


@Generated
public class AuthorRequestDto {
    @NotNull
    @Size(max = 255)
    public String name;
    @NotNull
    @Size(max = 500)
    public String originalName;
    @NotNull
    public Date dateOfBirth;
    @NotNull
    @Size(max = 255)
    public String placeOfBirth;
    public Date dateOfDeath;
    @Size(min = 1, max = 255)
    public String placeOfDeath;
    @Size(min = 1, max = 500)
    public String about;
}
