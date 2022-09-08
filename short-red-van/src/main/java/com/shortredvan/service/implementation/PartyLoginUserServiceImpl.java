package com.shortredvan.service.implementation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserDeleted;
import com.shortredvan.entity.PartyLoginUserKey;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.FKConstraintException;
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
  public PartyLoginUser createPLU(PartyLoginUser plu) throws FKConstraintException {
    PartyLoginUserKey existingPLUK = plu.getId();
    if (pluRepository.findById(existingPLUK).isEmpty()) {
      try {
        return pluRepository.save(plu);
      } catch (DataIntegrityViolationException f) {
        throw new FKConstraintException("PartyId", "LoginUserId");
      }
    }
    return null;
  }


  @Override
  public void deletePLUsByLUId(LoginUser currentLogin) {
    List<PartyLoginUser> toDelete = getAllPLUsByLUId(currentLogin.getLoginUserId());
    if (!toDelete.isEmpty()) {
      for (PartyLoginUser plu : toDelete) {
        addToDeleted(plu, currentLogin);
        pluRepository.delete(plu);
      }
    }
  }

  @Override
  public void deletePLUsByPartyId(int id, LoginUser currentLogin) {
    List<PartyLoginUser> toDelete = getAllPLUsByPartyId(id);
    if (!toDelete.isEmpty()) {
      for (PartyLoginUser plu : toDelete) {
        addToDeleted(plu, currentLogin);
        pluRepository.delete(plu);
      }
    }
  }

  @Override
  public void addToDeleted(PartyLoginUser plu, LoginUser currentLogin) {
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
  public void deletePLU(PartyLoginUser plu, LoginUser currentLogin) {
    addToDeleted(plu,currentLogin);
    pluRepository.delete(plu);
    
  }


}
