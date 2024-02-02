package com.csw.catalog.dto.language;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;


public class LanguageUpdateRequestDto {

    @NotNull
    public long id;

    @Size(max = 255)
    public String name;


    @Size(max = 5)
    public String culture;
}
