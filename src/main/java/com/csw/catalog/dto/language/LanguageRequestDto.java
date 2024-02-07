package com.csw.catalog.dto.language;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Generated
public class LanguageRequestDto {

    @NotNull
    @Size(max = 255)
    public String name;

    @NotNull
    @Size(max = 5)
    public String culture;
}
