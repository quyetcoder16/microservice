package com.microservice.bookservice.command.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestModel {
    private String id;

    private String name;

    private String author;

    private Boolean isReady;
}
