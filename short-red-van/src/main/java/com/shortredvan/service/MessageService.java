package com.shortredvan.service;

import java.util.List;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Message;

public interface MessageService {
  public List<Message> findAllMessages();
  
  public Message getMessageById(int id);
  
  public Message createMessage(Message message, LoginUser currentLogin);
  
  public Message updateMessage(Message message, int id, LoginUser currentLogin);
  
  public void deleteMessageById(int id, LoginUser currentLogin);
  
  public List<Message> getByCreatedBy(int loginUserId);

  public List<Message> getByParentMessageId(int parentId);
  
  public List<Message> getByPartyId(int partyId);

}
