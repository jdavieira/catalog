package com.csw.catalog.dto.book;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Generated;

@Generated
public class BookSellDto {
    @NotNull
    public long id;

    @NotNull
    @Min(1)
    public int stock;
}
