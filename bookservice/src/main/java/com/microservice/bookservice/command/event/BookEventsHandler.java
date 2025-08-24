package com.microservice.bookservice.command.event;

import com.microservice.bookservice.command.data.Book;
import com.microservice.bookservice.command.data.BookRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {

    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent bookCreatedEvent) {
        Book book = new Book();
        BeanUtils.copyProperties(bookCreatedEvent, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent bookUpdatedEvent) {
        Optional<Book> optionalBook = bookRepository.findById(bookUpdatedEvent.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BeanUtils.copyProperties(bookUpdatedEvent, book);
            bookRepository.save(book);
        }
    }

    @EventHandler
    public void on(BookDeletedEvent bookDeletedEvent) {
        Optional<Book> optionalBook = bookRepository.findById(bookDeletedEvent.getId());
        optionalBook.ifPresent(bookRepository::delete);
    }

}
