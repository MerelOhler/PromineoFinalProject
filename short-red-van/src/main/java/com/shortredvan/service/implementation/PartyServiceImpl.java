package com.shortredvan.service.implementation;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;
import com.shortredvan.entity.UserRole;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.NoAccessException;
import com.shortredvan.exception.ResourceNotFoundException;
import com.shortredvan.repository.PartyRepository;
import com.shortredvan.service.PartyLoginUserService;
import com.shortredvan.service.PartyService;

@Service
public class PartyServiceImpl implements PartyService {
  private PartyRepository partyRepository;
  private PartyLoginUserService pluService;
  
  @Autowired
  public PartyServiceImpl(PartyRepository partyRepository, PartyLoginUserService pluService) {
    this.partyRepository = partyRepository;
    this.pluService = pluService;
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
  public List<Party> getPartiesByLU(int id) {
    return partyRepository.findByLU(id);
  }

  @Override
  public Party createParty(Party party, LoginUser currentLogin) {
    party.setCreatedBy(currentLogin.getLoginUserId());
    party.setAdminUserId(currentLogin.getLoginUserId());
    try {
      return partyRepository.save(party);
    } catch (DataIntegrityViolationException e) {
      new DuplicateFoundException("Party", "Name", party.getName());
      return null;
    }
  }

  @Override
  public Party updateParty(Party party, int id, LoginUser currentLogin) {
    try {
      Party existingParty = partyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Party","ID", id));
      
      if (currentLogin.getUserRole() != UserRole.ADMIN && existingParty.getAdminUserId() != currentLogin.getLoginUserId()) {
        throw new NoAccessException("Party", currentLogin.getLoginUserId(), id);
      }
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
  public void deletePartyById(int id, LoginUser currentLogin) {
    Party deleteParty = getPartyById(id);
    if (currentLogin.getUserRole() != UserRole.ADMIN && deleteParty.getAdminUserId() != currentLogin.getLoginUserId()) {
      throw new NoAccessException("Party", currentLogin.getLoginUserId(), id);
    }
    updateParty(deleteParty, id, currentLogin);
    pluService.deletePLUsByPartyId(id, currentLogin);
    partyRepository.deleteById(id);
  }

}
