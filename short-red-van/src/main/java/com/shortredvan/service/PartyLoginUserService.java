package com.shortredvan.service;

import java.util.List;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserKey;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.FKConstraintException;


public interface PartyLoginUserService {
  
  public PartyLoginUser getPLUById(PartyLoginUserKey pluk);
  
  public List<PartyLoginUser> getAllPLUs();
  
  public List<PartyLoginUser> getAllPLUsByLUId(int loginUserId);
  
  public List<PartyLoginUser> getAllPLUsByPartyId(int partyId);
  
  public PartyLoginUser createPLU(PartyLoginUser plu) throws FKConstraintException;
  
  //no updates only deletes
  
  public void deletePLUsByLUId(LoginUser currentLogin);
  
  public void deletePLUsByPartyId(int id, LoginUser currentLogin);
  
  public void addToDeleted (PartyLoginUser plu, LoginUser currentLogin); 
  
  public void deletePLU (PartyLoginUser plu, LoginUser currentLogin);

}
