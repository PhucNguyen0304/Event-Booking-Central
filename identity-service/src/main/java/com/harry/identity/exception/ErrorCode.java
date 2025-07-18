package com.harry.identity.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    CANNOT_SEND_EMAIL(1008, "Cannot send email", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1009, "User exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1010, "User not exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1011,"Role not found", HttpStatus.NOT_FOUND)
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
