package com.harry.chat_service.service;

import com.harry.chat_service.dto.request.ConversationRequest;
import com.harry.chat_service.dto.response.ConversationResponse;
import com.harry.chat_service.entity.Conversation;
import com.harry.chat_service.entity.ParticipantInfo;
import com.harry.chat_service.exception.AppException;
import com.harry.chat_service.exception.ErrorCode;
import com.harry.chat_service.mapper.ConversationMapper;
import com.harry.chat_service.repository.ConversationRepository;
import com.harry.chat_service.repository.httpClient.MyInfoClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationService {

    MyInfoClient myInfoClient;
    ConversationRepository conversationRepository;
    ConversationMapper conversationMapper;

    public List<ConversationResponse> myConversations() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User id" + userId);
        List<Conversation> conversations = conversationRepository.findAllByParticipantIdsContains(userId);

        return conversations.stream().map(this::toConversationResponse).toList();
    }

    public ConversationResponse create(ConversationRequest request) {
        log.info("Create conversation service");
        // Fetch user infos
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var userInfoResponse = myInfoClient.getMyInfor(userId);
        var participantInfoResponse = myInfoClient.getMyInfor(
                request.getParticipantIds().getFirst());

        log.info("User infor response " + userInfoResponse);
        log.info("participantInfoResponse " + participantInfoResponse);
        if(Objects.isNull(userInfoResponse) || Objects.isNull(participantInfoResponse)) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        var userInfo = userInfoResponse.getResult();
        var participantInfo = participantInfoResponse.getResult();

        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        userIds.add(participantInfo.getId());

        var sortedIds = userIds.stream().sorted().toList();
        String userIdHash = generateParticipantHash(sortedIds);
        log.info("User ids hash " + userIdHash);
        var conversation = conversationRepository.findByParticipantsHash(userIdHash)
                .orElseGet(() -> {
                    List<ParticipantInfo> participantInfos = List.of(
                            ParticipantInfo.builder()
                                    .userId(userInfo.getId())
                                    .username(userInfo.getUsername())
                                    .lastName(userInfo.getLastName())
                                    .avatar(userInfo.getAvatar())
                                    .build(),
                            ParticipantInfo.builder()
                                    .userId(participantInfo.getId())
                                    .username(participantInfo.getUsername())
                                    .firstName(participantInfo.getFirstName())
                                    .lastName(participantInfo.getLastName())
                                    .avatar(participantInfo.getAvatar())
                                    .build()
                    );

                    // Build Conversation Info
                    Conversation newConversation = Conversation.builder()
                            .type(request.getType())
                            .participantsHash(userIdHash)
                            .createdDate(Instant.now())
                            .modifiedDate(Instant.now())
                            .participants(participantInfos)
                            .build();

                    return conversationRepository.save(newConversation);
                }) ;
        return toConversationResponse(conversation);
    }

    private String generateParticipantHash(List<String> userIds) {
        StringJoiner stringJoiner = new StringJoiner("_");
        userIds.forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    private ConversationResponse toConversationResponse(Conversation conversation) {
        log.info("to conversationResponse method : Conversation " + conversation);
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        ConversationResponse conversationResponse = conversationMapper.toConvarsationResponse(conversation);

        conversation.getParticipants().stream()
                .filter(participantInfo -> !participantInfo.getUserId().equals(currentUserId))
                .findFirst().ifPresent(participantInfo -> {
                    conversationResponse.setConversationName(participantInfo.getUsername());
                    conversationResponse.setConversationAvatar(participantInfo.getAvatar());
                });
        return conversationResponse;
    }
}

