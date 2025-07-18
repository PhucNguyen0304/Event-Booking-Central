package com.harry.event_service.mapper;

import com.harry.event_service.dto.request.EventCreationRequest;
import com.harry.event_service.dto.response.EventResponse;
import com.harry.event_service.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventCreationRequest request);

    EventResponse toEventResponse(Event event);
}
