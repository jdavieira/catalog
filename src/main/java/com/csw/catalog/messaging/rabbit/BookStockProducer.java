package com.csw.catalog.messaging.rabbit;

import com.csw.catalog.messaging.event.UpdateBookStockEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Generated;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Generated
@ApplicationScoped
@JBossLog
public class BookStockProducer {

    @Channel("catalog-stock")
    Emitter<UpdateBookStockEvent> requestEmitter;

    public void sendBockStockRequestMessage(long bookId, int stock) {
        log.info("Book stock request event sent: " + bookId + " - " + stock);

        requestEmitter.send(new UpdateBookStockEvent(bookId, stock));
    }
}
