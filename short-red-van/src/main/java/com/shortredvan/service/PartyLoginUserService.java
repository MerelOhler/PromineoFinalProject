package com.shortredvan.service;

import java.util.List;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserKey;


public interface PartyLoginUserService {
  
  public PartyLoginUser getPLUById(PartyLoginUserKey pluk);
  
  public List<PartyLoginUser> getAllPLUs();
  
  public List<PartyLoginUser> getAllPLUsByLUId(int loginUserId);
  
  public List<PartyLoginUser> getAllPLUsByPartyId(int partyId);
  
  public PartyLoginUser createPLU(PartyLoginUser plu);
  
  //no updates only deletes
  
  public void deletePLUsByLUId(int id, CurrentLogin currentLogin);
  
  public void deletePLUsByPartyId(int id, CurrentLogin currentLogin);
  
  public void addToDeleted (PartyLoginUser plu, CurrentLogin currentLogin); 
  
  public void deletePLU (PartyLoginUser plu, CurrentLogin currentLogin);

}
