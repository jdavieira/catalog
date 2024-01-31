package com.csw.catalog.dto.language;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDto {
    public int id;

    public String name;

    public String culture;
}
