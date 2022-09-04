package com.shortredvan.service;

import java.util.List;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;

public interface LoginUserService {
  
  public LoginUser getLoginUserById(int id);
  
  public List<LoginUser> getAllLoginUsers();
  
  public LoginUser createLoginUser(LoginUser loginUser);
  
  public LoginUser updateLoginUser(LoginUser loginUser, int id, CurrentLogin currentLogin);
  
  public void deleteLoginUserById(int id, CurrentLogin currentLogin);
  
  public LoginUser getLoginUserByEmail(String email);

  public List<LoginUser> getLoginUsers4PartyId(int id);
  
  //public PartyLoginUser addParty();

}
