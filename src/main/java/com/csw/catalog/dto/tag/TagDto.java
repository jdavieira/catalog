package com.csw.catalog.dto.tag;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto {
    public int id;

    public String name;
}
