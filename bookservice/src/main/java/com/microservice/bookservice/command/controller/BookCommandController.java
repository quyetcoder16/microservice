package com.microservice.bookservice.command.controller;

import com.microservice.bookservice.command.command.CreateBookCommand;
import com.microservice.bookservice.command.command.DeleteBookCommand;
import com.microservice.bookservice.command.command.UpdateBookCommand;
import com.microservice.bookservice.command.model.BookRequestModel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    private String addBook(@RequestBody BookRequestModel bookRequestModel) {
        CreateBookCommand createBookCommand = new CreateBookCommand(
                UUID.randomUUID().toString(),
                bookRequestModel.getName(),
                bookRequestModel.getAuthor(),
                true
        );
        return commandGateway.sendAndWait(createBookCommand);

    }

    @PutMapping("/{bookId}")
    private String updateBook(@RequestBody BookRequestModel bookRequestModel,
                              @PathVariable String bookId) {
        UpdateBookCommand updateBookCommand = new UpdateBookCommand(
                bookId,
                bookRequestModel.getName(),
                bookRequestModel.getAuthor(),
                bookRequestModel.getIsReady()
        );
        return commandGateway.sendAndWait(updateBookCommand);
    }

    @DeleteMapping("/{bookId}")
    private String deleteBook(@PathVariable String bookId) {
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(bookId);
        return commandGateway.sendAndWait(deleteBookCommand);
    }

}
