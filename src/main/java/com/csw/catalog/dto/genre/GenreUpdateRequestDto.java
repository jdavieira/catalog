package com.csw.catalog.dto.genre;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;


public class GenreUpdateRequestDto {
    @NotNull
    public long id;

    @Size(max = 255)
    public String name;
}
