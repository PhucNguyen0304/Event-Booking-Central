package com.harry.chat_service.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.harry.chat_service.dto.request.ChatMessageRequest;
import com.harry.chat_service.dto.response.ChatMessageResponse;
import com.harry.chat_service.entity.ChatMessage;
import com.harry.chat_service.entity.ParticipantInfo;
import com.harry.chat_service.exception.AppException;
import com.harry.chat_service.exception.ErrorCode;
import com.harry.chat_service.mapper.ChatMessageMapper;
import com.harry.chat_service.repository.ChatMessageRepository;
import com.harry.chat_service.repository.ConversationRepository;
import com.harry.chat_service.repository.httpClient.MyInfoClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ChatMessageService {
    SocketIOServer socketIOServer;
    MyInfoClient myInfoClient;
    ConversationRepository conversationRepository;
    ChatMessageMapper chatMessageMapper;
    ChatMessageRepository chatMessageRepository;

    public List<ChatMessageResponse> getMessages(String conversationId) {
        log.info("Get Message Service");
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        conversationRepository.findById(conversationId).orElseThrow(()-> new AppException(ErrorCode.CONVERSATION_NOT_FOUND))
                .getParticipants()
                .stream()
                .filter(participantInfo -> userId.equals(participantInfo.getUserId()))
                .findAny()
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        var messages = chatMessageRepository.findAllByConversationIdOrderByCreatedDateDesc(conversationId);
        log.info("List messages " + messages);
        return messages.stream().map(this::toChatMessageResponse).toList();
    }

    public ChatMessageResponse create(ChatMessageRequest request) {
        log.info("Create Chat Service " + request);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        // Validate conversationId
        conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND))
                .getParticipants()
                .stream()
                .filter(participantInfo -> userId.equals(participantInfo.getUserId()))
                .findAny()
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));



        // Get User Info from my info client
        var userResponse =  myInfoClient.getMyInfor(userId);
        if(Objects.isNull(userResponse)) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        var userInfo = userResponse.getResult();

        log.info("User Info " + userInfo);
        // Build Chat Message Info
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(request);
        chatMessage.setSender(ParticipantInfo.builder()
                        .userId(userInfo.getId())
                        .username(userInfo.getUsername())
                        .firstName(userInfo.getFirstName())
                        .lastName(userInfo.getLastName())
                        .avatar(userInfo.getAvatar())
                .build());
        chatMessage.setCreatedDate(Instant.now());

        log.info("Chat MSG " + chatMessage);
        // Create chat message
        chatMessage = chatMessageRepository.save(chatMessage);
        String message = chatMessage.getMessage();

        // Public socket event to clients
        socketIOServer.getAllClients().forEach(client -> {
            client.sendEvent("message", message);
        });

        // Convert to Response
        return toChatMessageResponse(chatMessage);
    }

    public ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ChatMessageResponse chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);

        chatMessageResponse.setMe(userId.equals(chatMessage.getSender().getUserId()));
        log.info("To chat msg response " + chatMessageResponse);
        return chatMessageResponse;
    }

}

