package com.shortredvan.service.implementation;


import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Message;
import com.shortredvan.entity.Party;
import com.shortredvan.entity.UserRole;
import com.shortredvan.exception.NoAccessException;
import com.shortredvan.exception.NotInGivenPartyException;
import com.shortredvan.exception.ResourceNotFoundException;
import com.shortredvan.repository.MessageRepository;
import com.shortredvan.service.MessageService;
import com.shortredvan.service.PartyService;

@Service
public class MessageServiceImpl implements MessageService {
  
  private MessageRepository messageRepository;
  private PartyService partyService;
  
  @Autowired
  public MessageServiceImpl(MessageRepository messageRepository, PartyService partyService) {
    this.messageRepository = messageRepository;
    this.partyService = partyService;
  } 

  @Override
  public List<Message> findAllMessages() {
    return messageRepository.findAll();
  }

  @Override
  public Message getMessageById(int id) {
    return messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message", "messageId", id));
  }

  @Override
  public Message createMessage(Message message, LoginUser currentLogin) throws NotInGivenPartyException{
    List<Party> currentLoginParties = partyService.getPartiesByLU(currentLogin.getLoginUserId());
    Party messageParty = partyService.getPartyById(message.getPartyId());
    if (!currentLoginParties.contains(messageParty)) {
      throw new NotInGivenPartyException("Login user is not in the party selected for the message to go in.");
    }
    
    if (message.getParentMessageId() <= 0) {
      message.setParentMessageId(null);
    } else if (message.getParentMessageId() > 0) {
      int parentPartyId = getMessageById(message.getParentMessageId()).getPartyId();
    
      if (parentPartyId != message.getPartyId()) {
        throw new NotInGivenPartyException("Given party id: " + message.getPartyId() +
            " and the party id of the parent message: " + parentPartyId + " are not the same.");
      }
    }

    message.setCreatedBy(currentLogin.getLoginUserId());
    return messageRepository.save(message);
  }

  @Override
  public Message updateMessage(Message message, int id, LoginUser currentLogin) throws NoAccessException {
    Message currentMessage = getMessageById(id);
    int partyAdmin = partyService.getPartyById(currentMessage.getPartyId()).getAdminUserId();
    if (currentLogin.getLoginUserId() != currentMessage.getCreatedBy() && currentLogin.getUserRole() != UserRole.ADMIN 
        && currentLogin.getLoginUserId() != partyAdmin) {
      throw new NoAccessException("Message", currentLogin.getLoginUserId(), id);
    }
    currentMessage.setMessageText(message.getMessageText());
    currentMessage.setDateModified(new Timestamp(System.currentTimeMillis()));
    currentMessage.setModifiedBy(currentLogin.getLoginUserId());
    return messageRepository.save(currentMessage);
  }

  @Override
  public void deleteMessageById(int id, LoginUser currentLogin) {
    Message currentMessage = getMessageById(id);
    int partyAdmin = partyService.getPartyById(currentMessage.getPartyId()).getAdminUserId();
    if (currentLogin.getLoginUserId() != currentMessage.getCreatedBy() && currentLogin.getUserRole() != UserRole.ADMIN 
        && currentLogin.getLoginUserId() != partyAdmin) {
      throw new NoAccessException("Message", currentLogin.getLoginUserId(), id);
    }

    currentMessage.setDateDeleted(new Timestamp(System.currentTimeMillis()));
    currentMessage.setDeletedBy(currentLogin.getLoginUserId());
    messageRepository.save(currentMessage);

  }

  @Override
  public List<Message> getByCreatedBy(int loginUserId) {
    return messageRepository.findByCreatedBy(loginUserId);
  }

  @Override
  public List<Message> getByParentMessageId(int parentId) {
    return messageRepository.findByParentMessageId(parentId);
  }

  @Override
  public List<Message> getByPartyId(int partyId) {
    return messageRepository.findByPartyId(partyId);
  }

}
