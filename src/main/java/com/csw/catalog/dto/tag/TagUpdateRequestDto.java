package com.csw.catalog.dto.tag;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Generated
public class TagUpdateRequestDto {

    @NotNull
    public long id;

    @Size(max = 255)
    public String name;
}
