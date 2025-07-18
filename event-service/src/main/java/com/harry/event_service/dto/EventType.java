package com.harry.event_service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EventType {
    MOVIE("Vé xem phim"),
    CIRCUS("Xem xiếc"),
    CONCERT("Ca nhạc");

    private final String displayName;

}
