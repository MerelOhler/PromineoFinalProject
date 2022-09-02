package com.shortredvan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentLogin {

  private int loginUserId;
  private String email;
  private String password;

}
