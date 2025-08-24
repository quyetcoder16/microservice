package com.microservice.bookservice.command.aggregate;

import com.microservice.bookservice.command.command.CreateBookCommand;
import com.microservice.bookservice.command.command.DeleteBookCommand;
import com.microservice.bookservice.command.command.UpdateBookCommand;
import com.microservice.bookservice.command.event.BookCreatedEvent;
import com.microservice.bookservice.command.event.BookDeletedEvent;
import com.microservice.bookservice.command.event.BookUpdatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Data
public class BookAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isReady;

//    @CommandHandler
//    public void BookAggregate(CreateBookCommand command) {
//        BookCreatedEvent event = new BookCreatedEvent();
//        BeanUtils.copyProperties(command, event);
//        AggregateLifecycle.apply(event);
//    }
//


    // Constructor xử lý lệnh tạo mới (Axon gọi khi aggregate chưa tồn tại)
    @CommandHandler
    public BookAggregate(CreateBookCommand command) {
        // Áp dụng sự kiện để tạo trạng thái aggregate
        AggregateLifecycle.apply(new BookCreatedEvent(
                command.getId(),
                command.getName(),
                command.getAuthor(),
                command.getIsReady()
        ));
    }

    @CommandHandler
    public void handle(UpdateBookCommand command) {
        // Áp dụng sự kiện để tạo trạng thái aggregate
        AggregateLifecycle.apply(new BookUpdatedEvent(
                command.getId(),
                command.getName(),
                command.getAuthor(),
                command.getIsReady()
        ));
    }

    @CommandHandler
    public void handle(DeleteBookCommand command) {
        // Áp dụng sự kiện để tạo trạng thái aggregate
        AggregateLifecycle.apply(new BookDeletedEvent(
                command.getId()
        ));
    }

    // Trình xử lý sự kiện để xây dựng/tái tạo trạng thái aggregate từ sự kiện
    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.id = event.getId();
    }


}
