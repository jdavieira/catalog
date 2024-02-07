package com.csw.catalog.messaging.event;

import lombok.*;

@Generated
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookStockEvent {

    public long bookId;

    public int stock;
}
