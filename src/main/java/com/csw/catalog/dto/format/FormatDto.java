package com.csw.catalog.dto.format;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormatDto {
    public int id;


    public String name;
}
