package com.shortredvan.service.implementation;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.ResourceNotFoundException;
import com.shortredvan.repository.PartyRepository;
import com.shortredvan.service.PartyService;

@Service
public class PartyServiceImpl implements PartyService {
  private PartyRepository partyRepository;
  
  @Autowired
  public PartyServiceImpl(PartyRepository partyRepository) {
    this.partyRepository = partyRepository;
  }

  @Override
  public Party getPartyById(int id) {
    return partyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Party","ID",id));
  }

  @Override
  public List<Party> getAllParties() {
    return partyRepository.findAll();
  }

  @Override
  public Party createParty(Party party, CurrentLogin currentLogin) {
    party.setCreatedBy(currentLogin.getLoginUserId());
    try {
      return partyRepository.save(party);
    } catch (DataIntegrityViolationException e) {
      new DuplicateFoundException("Party", "Name", party.getName());
      return null;
    }
  }

  @Override
  public Party updateParty(Party party, int id, CurrentLogin currentLogin) {
    try {
      Party existingParty = partyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Party","ID", id));
      existingParty.setName(party.getName());
      existingParty.setAdminUserId(party.getAdminUserId());
      existingParty.setDateModified(new Timestamp(System.currentTimeMillis()));
      existingParty.setModifiedBy(currentLogin.getLoginUserId());
      return partyRepository.save(existingParty);
    } catch (DataIntegrityViolationException e) {
      new DuplicateFoundException("Party", "Name", party.getName());
      return null;
    }
  }

  @Override
  public void deletePartyById(int id, CurrentLogin currentLogin) {
    // TODO Auto-generated method stub

  }

}
