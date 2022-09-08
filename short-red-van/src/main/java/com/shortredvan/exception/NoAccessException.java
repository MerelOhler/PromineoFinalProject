 package com.shortredvan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.Data;

@Data
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoAccessException extends RuntimeException{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int loginUserId;
  private int messageId;

  public NoAccessException(String entity, int loginUserId, int messageId) {

      super(String.format("%s could not be upated, Login User ID: %s is not the administrator of %s %s", entity, loginUserId, entity, messageId));
      this.loginUserId = loginUserId;
      this.messageId = messageId;
      System.err.println(getMessage());
  }
  

}
