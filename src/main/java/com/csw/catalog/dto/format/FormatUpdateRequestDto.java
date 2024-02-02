package com.csw.catalog.dto.format;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;

public class FormatUpdateRequestDto {

    @NotNull
    public long id;

    @Size(max = 255)
    public String name;
}