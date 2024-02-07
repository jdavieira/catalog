package com.csw.catalog.dto.format;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Generated
public class FormatRequestDto {
    @NotNull
    @Size(max = 255)
    public String name;
}
