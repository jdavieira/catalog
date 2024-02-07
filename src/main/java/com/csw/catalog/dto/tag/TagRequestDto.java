package com.csw.catalog.dto.tag;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Generated
public class TagRequestDto {
    @NotNull
    @Size(max = 255)
    public String name;
}
