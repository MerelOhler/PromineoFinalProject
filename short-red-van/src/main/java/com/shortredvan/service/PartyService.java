package com.shortredvan.service;

import java.util.List;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;

public interface PartyService {
  public Party getPartyById(int id);
  
  public List<Party> getAllParties();
  
  public Party createParty(Party party, LoginUser currentLogin);
  
  public Party updateParty(Party party, int id, LoginUser currentLogin);
  
  public void deletePartyById(int id, LoginUser currentLogin);
  
  public List<Party> getPartiesByLU(int id);

}
