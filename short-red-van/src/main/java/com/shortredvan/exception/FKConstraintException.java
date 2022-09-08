package com.shortredvan.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FKConstraintException extends SQLIntegrityConstraintViolationException {
  
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String foreignkey1;
  private String foreignkey2;

  
  public FKConstraintException(String foreignkey1, String foreignkey2) {
    super(String.format("Foreign key constraint has been violated for %s or %s", foreignkey1, foreignkey2));
    this.foreignkey1 = foreignkey1;
    this.foreignkey2 = foreignkey2;
    System.err.println(getMessage());
  }

  public String getForeignKey1() {
    return foreignkey1;
  }

  public String getForeignKey2() {
    return foreignkey2;
  }


}
