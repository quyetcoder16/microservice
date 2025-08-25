package com.microservice.bookservice.command.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCreatedEvent {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
