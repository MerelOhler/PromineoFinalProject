package com.shortredvan.service;

import java.util.List;
import com.shortredvan.entity.LoginUser;

public interface LoginUserService {
  
  public LoginUser getLoginUserById(int id);
  
  public List<LoginUser> getAllLoginUsers();
  
  public LoginUser createLoginUser(LoginUser loginUser);
  
  public LoginUser updateLoginUser(LoginUser loginUser, int id, LoginUser currentLogin);
  
  public void deleteLoginUserById(LoginUser currentLogin);
  
  public LoginUser getLoginUserByEmail(String email);

  public List<LoginUser> getLoginUsers4PartyId(int id);

}
