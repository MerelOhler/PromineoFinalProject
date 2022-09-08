package com.shortredvan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.Data;

@Data
@ResponseStatus(HttpStatus.CONFLICT)
public class NotInGivenPartyException extends RuntimeException{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String message;
    
  public NotInGivenPartyException(String message) {
    super(String.format("%s Message cannot be created", message));
    this.message = message;
    System.err.println(getMessage());
  }

}
