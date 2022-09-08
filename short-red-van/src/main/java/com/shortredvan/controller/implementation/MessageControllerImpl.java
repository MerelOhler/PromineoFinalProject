package com.shortredvan.controller.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shortredvan.ShortRedVan;
import com.shortredvan.controller.MessageController;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Message;
import com.shortredvan.exception.NotLoggedInException;
import com.shortredvan.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageControllerImpl implements MessageController {
  
  private LoginUser currentLogin;
  private ShortRedVan srv;
  private MessageService messageService;
  
  @Autowired
  public MessageControllerImpl (ShortRedVan srv, MessageService messageService) {
    this.srv = srv;
    this.messageService= messageService; 
  }
  
  private void getCurrentLU() {
    currentLogin = srv.getCurrentLogin();
    if(currentLogin == null || currentLogin.getLoginUserId() == 0) {
      throw new NotLoggedInException();
    }
  }

  @Override
  public List<Message> getAllMessages() {
    getCurrentLU();
    return messageService.findAllMessages();
  }

  @Override
  public ResponseEntity<Message> getMessageById(int id) {
    getCurrentLU();
    return new ResponseEntity<Message>(messageService.getMessageById(id),HttpStatus.OK);
  }

  @Override
  public List<Message> getMessages4PartyId(int id) {
    getCurrentLU();
    return messageService.getByPartyId(id);
  }

  @Override
  public List<Message> getMessages4LUId(int id) {
    getCurrentLU();
    return messageService.getByCreatedBy(id);
  }

  @Override
  public List<Message> getMessages4ParentId(int id) {
    getCurrentLU();
    return messageService.getByParentMessageId(id);
  }

  @Override
  public ResponseEntity<Message> createMessage(Message message) {
    getCurrentLU();
    return new ResponseEntity<Message>(messageService.createMessage(message, currentLogin), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Message> updateMessage(int id, Message message) {
    getCurrentLU();
    return new ResponseEntity<Message>(messageService.updateMessage(message, id, currentLogin), HttpStatus.OK);
  }

  @Override
  public String deleteMessage(int id) {
    getCurrentLU();
    messageService.deleteMessageById(id, currentLogin);
    return "Message has been date deleted";
  }

}
