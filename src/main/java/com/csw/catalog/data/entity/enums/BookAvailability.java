package com.csw.catalog.data.entity.enums;

import lombok.Generated;
import lombok.Getter;

@Generated
@Getter
public enum BookAvailability {
    TO_BE_LAUNCHED(0),
    ON_PRE_ORDER(1),
    ON_ORDER(2),
    AVAILABLE(3);

    private final int value;

    BookAvailability(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
