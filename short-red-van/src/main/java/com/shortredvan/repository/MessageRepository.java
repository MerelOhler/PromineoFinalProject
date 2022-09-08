package com.shortredvan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shortredvan.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
  
  List<Message> findByCreatedBy (int createdBy);
  
  List<Message> findByParentMessageId (int parentMessageId);
  
  List<Message> findByPartyId (int partyId);

}
