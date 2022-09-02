package com.shortredvan.controller.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shortredvan.ShortRedVan;
import com.shortredvan.controller.PartyController;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.NotLoggedInException;
import com.shortredvan.service.PartyService;

@RestController
@RequestMapping("/party")
public class PartyControllerImpl implements PartyController {
  private CurrentLogin currentLogin;
  private ShortRedVan srv;
  private PartyService partyService;
  
  @Autowired
  public PartyControllerImpl(PartyService partyService, ShortRedVan srv) {
    this.partyService = partyService;
    this.srv = srv;
  }
  
  private void getCurrentLU() {
    currentLogin = srv.getCurrentLogin();
    if(currentLogin == null) {
      throw new NotLoggedInException();
    }
  }

  @Override
  public List<Party> getAllParties() {
    getCurrentLU();
    return partyService.getAllParties();
  }

  @Override
  public ResponseEntity<Party> getPartyById(int id) {
    getCurrentLU();
    return new ResponseEntity<Party>(partyService.getPartyById(id),HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Party> createParty(Party party) throws DuplicateFoundException {
    getCurrentLU();
    ResponseEntity<Party> response = new ResponseEntity<Party>(partyService.createParty(party, currentLogin),HttpStatus.CREATED);
    if(response.getBody() == null) {
      throw new DuplicateFoundException("Party", "name", party.getName());
    }
    return response;
  }

  @Override
  public ResponseEntity<Party> updateParty(int id, Party party) throws DuplicateFoundException {
    getCurrentLU();
    if (currentLogin == null) {
      throw new NotLoggedInException();
    }
    ResponseEntity<Party> response = new ResponseEntity<Party>(partyService.updateParty(party, id, currentLogin), HttpStatus.OK);
    if(response.getBody() == null) {
      throw new DuplicateFoundException("Party", "name", party.getName());
    }
    return response;
  }

  @Override
  public String deleteParty(int id) {
    getCurrentLU();
    partyService.deletePartyById(id, currentLogin);
    return "LoginUser has been deleted";
  }

}
