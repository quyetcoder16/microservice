package com.microservice.bookservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookCommand {
    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isReady;
}
