package com.shortredvan.controller.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shortredvan.ShortRedVan;
import com.shortredvan.controller.PartyLoginUserController;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserKey;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.NotLoggedInException;
import com.shortredvan.service.PartyLoginUserService;

@RestController
@RequestMapping("/partyloginuser")
public class PartyLoginUserControllerImpl implements PartyLoginUserController {
  private PartyLoginUserService pluService;
  private CurrentLogin currentLogin;
  private ShortRedVan srv;
  
  @Autowired
  public PartyLoginUserControllerImpl(PartyLoginUserService pluService, ShortRedVan srv) {
    this.pluService = pluService;
    this.srv = srv;
  }
  
  private void getCurrentLU() {
    currentLogin = srv.getCurrentLogin();
    if(currentLogin == null) {
      throw new NotLoggedInException();
    }
  }
  

  @Override
  public List<PartyLoginUser> getAllPLUs() {
    getCurrentLU();
    return pluService.getAllPLUs();
  }

  @Override
  public ResponseEntity<PartyLoginUser> getPLUById(int partyId, int loginUserId) {
    getCurrentLU();
    PartyLoginUserKey id = new PartyLoginUserKey();
    id.setPartyId(partyId);
    id.setLoginUserId(loginUserId);
    return new ResponseEntity<PartyLoginUser>(pluService.getPLUById(id),HttpStatus.OK);
  }

  @Override
  public ResponseEntity<PartyLoginUser> createPLU(int partyId, int loginUserId) throws DuplicateFoundException {
    getCurrentLU();
    PartyLoginUserKey id = new PartyLoginUserKey();
    id.setPartyId(partyId);
    id.setLoginUserId(loginUserId);
    PartyLoginUser plu = new PartyLoginUser();
    plu.setId(id);
    plu.setCreatedBy(currentLogin.getLoginUserId());
    ResponseEntity<PartyLoginUser> response = new ResponseEntity<PartyLoginUser>(pluService.createPLU(plu),HttpStatus.CREATED);
    System.out.println(response.getBody().getCreatedBy());
    if(response.getBody() == null) {
      throw new DuplicateFoundException("PartyLoginUser", "Party and LoginUser", plu.getId().getPartyId() + " and " + plu.getId().getLoginUserId());
    }
    return response;
  }

  @Override
  public String deletePLU(int partyId, int loginUserId) {
    getCurrentLU();
    PartyLoginUserKey id = new PartyLoginUserKey();
    id.setPartyId(partyId);
    id.setLoginUserId(loginUserId);
    PartyLoginUser plu = pluService.getPLUById(id);
    pluService.deletePLU(plu, currentLogin);
    return "Connection has been broken, loginuser is no longer in the party";
  }

}
