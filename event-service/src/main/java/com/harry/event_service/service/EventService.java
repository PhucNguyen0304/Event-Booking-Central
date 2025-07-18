package com.harry.event_service.service;

import com.harry.event_service.dto.request.EventCreationRequest;
import com.harry.event_service.dto.response.EventResponse;
import com.harry.event_service.entity.Event;
import com.harry.event_service.exception.AppException;
import com.harry.event_service.exception.ErrorCode;
import com.harry.event_service.mapper.EventMapper;
import com.harry.event_service.repository.EventRepository;
import com.harry.event_service.repository.httpClient.ImageClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EventService {
    EventRepository eventRepository;
    EventMapper eventMapper;
    ImageClient imageClient;

    @PreAuthorize("hasRole('ADMIN')")
    public EventResponse create(EventCreationRequest request, MultipartFile file) {
        log.info("Create Event : {}" + request);
        Event event = eventMapper.toEvent(request);

        List<MultipartFile> imgEvent = Collections.singletonList(file);

        List<String> imagesEvent= imageClient.uploadImage(imgEvent, "event");
        event.setImgEvent(imagesEvent.get(0));
        event.setTime(LocalTime.now());
        event.setDate(LocalDate.now());
        log.info("Event befor save " + event);
        return eventMapper.toEventResponse(eventRepository.save(event));
    }


    public List<EventResponse>  getEvents() {
        log.info("getEvents service");
        return  eventRepository.findAll().stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    public EventResponse getEvent(String eventId) {
        log.info("Get Event service");
        return eventMapper.toEventResponse(eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND)));
    }

    public EventResponse updateTicketCount(String eventId, int ticketCount) {
        log.info("Update ticket count service");
         Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
         event.setRemainingTickets(event.getRemainingTickets() - ticketCount);
         return  eventMapper.toEventResponse(eventRepository.save(event));
    }
}
