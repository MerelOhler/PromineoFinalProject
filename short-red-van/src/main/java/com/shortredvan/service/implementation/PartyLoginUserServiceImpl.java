package com.shortredvan.service.implementation;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserDeleted;
import com.shortredvan.entity.PartyLoginUserKey;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.ResourceNotFoundException;
import com.shortredvan.repository.PartyLoginUserDeletedRepository;
import com.shortredvan.repository.PartyLoginUserRepository;
import com.shortredvan.service.PartyLoginUserService;

@Service
public class PartyLoginUserServiceImpl implements PartyLoginUserService {

  private PartyLoginUserRepository pluRepository;
  private PartyLoginUserDeletedRepository pludRepository;

  @Autowired
  public PartyLoginUserServiceImpl(PartyLoginUserRepository pluRepository,
      PartyLoginUserDeletedRepository pludRepository) {
    this.pluRepository = pluRepository;
    this.pludRepository = pludRepository;
  }

  @Override
  public PartyLoginUser getPLUById(PartyLoginUserKey pluk) {
    return pluRepository.findById(pluk)
        .orElseThrow(() -> new ResourceNotFoundException("PartyLoginUser", "Id", pluk.toString()));
  }

  @Override
  public List<PartyLoginUser> getAllPLUs() {
    return pluRepository.findAll();
  }


  @Override
  public List<PartyLoginUser> getAllPLUsByLUId(int loginUserId) {
    return pluRepository.findByLUid(loginUserId);
  }

  @Override
  public List<PartyLoginUser> getAllPLUsByPartyId(int partyId) {
    return pluRepository.findByPartyid(partyId);
  }

  @Override
  public PartyLoginUser createPLU(PartyLoginUser plu) {
    try {
      return pluRepository.save(plu);
    } catch (DataIntegrityViolationException e) {
      new DuplicateFoundException("PartyLoginUser", "Id", plu.getId().toString());
      return null;
    }
  }


  @Override
  public void deletePLUsByLUId(int id, CurrentLogin currentLogin) {
    List<PartyLoginUser> toDelete = getAllPLUsByLUId(id);
    if (!toDelete.isEmpty()) {
      for (PartyLoginUser plu : toDelete) {
        addToDeleted(plu, currentLogin);
        pluRepository.delete(plu);
      }
    }
  }

  @Override
  public void deletePLUsByPartyId(int id, CurrentLogin currentLogin) {
    List<PartyLoginUser> toDelete = getAllPLUsByPartyId(id);
    if (!toDelete.isEmpty()) {
      for (PartyLoginUser plu : toDelete) {
        addToDeleted(plu, currentLogin);
        pluRepository.delete(plu);
      }
    }
  }

  @Override
  public void addToDeleted(PartyLoginUser plu, CurrentLogin currentLogin) {
    PartyLoginUserDeleted plud = new PartyLoginUserDeleted();
    plud.setPartyId(plu.getId().getPartyId());
    plud.setLoginUserId(plu.getId().getLoginUserId());
    plud.setDateCreated(plu.getDateCreated());
    plud.setCreatedBy(plu.getCreatedBy());
    plud.setDateDeleted(new Timestamp(System.currentTimeMillis()));
    plud.setDeletedBy(currentLogin.getLoginUserId());
    pludRepository.save(plud);
  }

  @Override
  public void deletePLU(PartyLoginUser plu, CurrentLogin currentLogin) {
    addToDeleted(plu,currentLogin);
    pluRepository.delete(plu);
    
  }


}
