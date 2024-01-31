package com.csw.catalog.messaging.rabbit;

import com.csw.catalog.messaging.event.UpdateBookStockEvent;
import com.csw.catalog.service.BookService;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.jbosslog.JBossLog;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;

@ApplicationScoped
@JBossLog
public class UpdateBookStockConsumer {

    private final BookService bookService;

    private final JobScheduler jobScheduler;

    public UpdateBookStockConsumer(BookService bookService, JobScheduler jobScheduler) {

        this.bookService = bookService;
        this.jobScheduler = jobScheduler;
    }

    //@Incoming("catalog")
    public void receiveUpdateBookStockEvent(JsonObject messageEvent) {

        var event = messageEvent.mapTo(UpdateBookStockEvent.class);
        try {
            log.info("Update Book Stock Event Received: " + event.bookId + " - " + event.stock);
            this.updateBookStock(event);
            log.info("Update Book Stock Event finished: " + event.bookId + " - " + event.stock);
        } catch (NotFoundException ex) {
            log.warn(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            jobScheduler.enqueue(() -> this.updateBookStock(event));
        }
    }

    @Job(name = "Update Book Stock", retries = 8)
    public void updateBookStock(UpdateBookStockEvent event) {

        bookService.updateBookStock(event.bookId, event.stock);
    }
}
