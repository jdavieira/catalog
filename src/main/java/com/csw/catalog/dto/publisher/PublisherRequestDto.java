package com.csw.catalog.dto.publisher;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Generated
public class PublisherRequestDto {
    @NotNull
    @Size(max = 255)
    public String name;
}
