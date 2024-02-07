package com.csw.catalog.dto.publisher;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Generated
public class PublisherUpdateRequestDto {

    @NotNull
    public long id;

    @Size(max = 255)
    public String name;
}
