package com.csw.catalog.dto.genre;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;


public class GenreRequestDto {
    @NotNull
    @Size(max = 255)
    public String name;
}
