package com.csw.catalog.messaging.rabbit;

import com.csw.catalog.messaging.event.UpdateBookStockEvent;
import io.vertx.core.json.JsonObject;


public class UpdateStockProducer {


    public JsonObject produceMessage() {

        JsonObject item = new JsonObject();

        var message = new UpdateBookStockEvent(1L, 1);

        item.put("UpdateBookStockEvent", message);

        return item;
    }
}
