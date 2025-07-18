package com.harry.chat_service.controller;

import com.harry.chat_service.dto.ApiResponse;
import com.harry.chat_service.dto.request.ConversationRequest;
import com.harry.chat_service.dto.response.ConversationResponse;
import com.harry.chat_service.service.ConversationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/conversations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationController {
    ConversationService conversationService;

    @PostMapping("/create")
    ApiResponse<ConversationResponse> createConversation(@RequestBody @Valid ConversationRequest request){
        log.info("Create conversations controller : request " + request);
        return ApiResponse.<ConversationResponse>builder()
                .result(conversationService.create(request))
                .build();
    }

    @GetMapping("/my-conversations")
    ApiResponse<List<ConversationResponse>> myConversations() {
        log.info("My Conversations Controller");
        return ApiResponse.<List<ConversationResponse>>builder()
                .result(conversationService.myConversations())
                 .build();
    }

}
