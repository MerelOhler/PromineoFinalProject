package com.shortredvan.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateFoundException extends SQLIntegrityConstraintViolationException{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String resourceName;
  private String fieldName;
  private Object fieldValue;
  
  public DuplicateFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s already exists with %s : %s", resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
    System.err.println(getMessage());
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }

}
