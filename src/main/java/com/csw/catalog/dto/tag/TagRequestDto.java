package com.csw.catalog.dto.tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagRequestDto {
    @NotNull
    @NotBlank(message = "Name may not be blank")
    @Size(max = 255)
    public String name;
}
