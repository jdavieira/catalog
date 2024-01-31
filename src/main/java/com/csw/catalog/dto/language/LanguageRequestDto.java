package com.csw.catalog.dto.language;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageRequestDto {

    @NotNull
    @NotBlank(message = "Name may not be blank")
    @Size(max = 255)
    public String name;

    @NotNull
    @NotBlank(message = "culture may not be blank")
    @Size(max = 5)
    public String culture;
}
