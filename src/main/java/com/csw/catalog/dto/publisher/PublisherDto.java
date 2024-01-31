package com.csw.catalog.dto.publisher;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublisherDto {
    public int id;

    public String name;
}
