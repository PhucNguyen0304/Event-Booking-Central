package com.harry.order_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NOT_ENOUGHT_TICKETS(1007, "Not Enough Tickets", HttpStatus.BAD_REQUEST),
    NOT_UPDATE_TICKETCOUNT(1008,"ERR When Update Ticket Count", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_NOT_FOUND(1009, "Order Not Found", HttpStatus.NOT_FOUND)
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
