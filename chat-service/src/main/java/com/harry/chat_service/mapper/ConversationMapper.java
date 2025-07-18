package com.harry.chat_service.mapper;

import com.harry.chat_service.dto.response.ConversationResponse;
import com.harry.chat_service.entity.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationResponse toConvarsationResponse(Conversation conversation);
}
