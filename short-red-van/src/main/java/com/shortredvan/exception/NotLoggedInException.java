package com.shortredvan.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotLoggedInException extends NullPointerException {
  public NotLoggedInException() {
    super("You are not logged in!");
    System.err.println(getMessage());
  }

}
