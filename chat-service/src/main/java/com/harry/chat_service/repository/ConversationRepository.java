package com.harry.chat_service.repository;

import com.harry.chat_service.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findByParticipantsHash(String userIdHash);

    @Query("{'participants.userId': ?0}")
    List<Conversation> findAllByParticipantIdsContains(String userId);
}
