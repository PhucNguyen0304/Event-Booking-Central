package com.harry.chat_service.mapper;

import com.harry.chat_service.dto.request.ChatMessageRequest;
import com.harry.chat_service.dto.response.ChatMessageResponse;
import com.harry.chat_service.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);
    ChatMessage toChatMessage(ChatMessageRequest chatMessageRequest);
    List<ChatMessageResponse> toChatMessageResponses(List<ChatMessage> chatMessages);
}
