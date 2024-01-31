package com.csw.catalog.dto.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorRequestDto {
    @NotNull
    @NotBlank(message = "Name may not be blank")
    @Size(max = 255)
    public String name;
    @NotNull
    @NotBlank(message = "Original name may not be blank")
    @Size(max = 500)
    public String originalName;
    @NotNull
    public Date dateOfBirth;
    @NotNull
    @NotBlank(message = "Place of birth may not be blank")
    @Size(max = 255)
    public String placeOfBirth;
    public Date dateOfDeath;
    @Size(min = 1, max = 255)
    public String placeOfDeath;
    @Size(min = 1, max = 500)
    public String about;
}
