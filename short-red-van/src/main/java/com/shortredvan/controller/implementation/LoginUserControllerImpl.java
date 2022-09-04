package com.shortredvan.controller.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shortredvan.ShortRedVan;
import com.shortredvan.controller.LoginUserController;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.NotLoggedInException;
import com.shortredvan.service.LoginUserService;

@RestController
@RequestMapping("/loginuser")
public class LoginUserControllerImpl implements LoginUserController{
  
  private LoginUserService loginUserService;
  private CurrentLogin currentLogin;
  private ShortRedVan srv;
  
  private void getCurrentLU() {
    currentLogin = srv.getCurrentLogin();
    if(currentLogin == null) {
      throw new NotLoggedInException();
    }
  }
  
  @Autowired
  public LoginUserControllerImpl(LoginUserService loginUserService, ShortRedVan srv) {
    this.loginUserService = loginUserService;
    this.srv = srv;
  }
  
  public List<LoginUser> getAllLU(){
    getCurrentLU();
    return loginUserService.getAllLoginUsers();
  }
  
  public ResponseEntity<LoginUser> getLUById(int id) {
    getCurrentLU();
    return new ResponseEntity<LoginUser>(loginUserService.getLoginUserById(id),HttpStatus.OK);
  }
  
  @Override
  public List<LoginUser> getLoginUsers4PartyId(int id) {
    getCurrentLU();
    return loginUserService.getLoginUsers4PartyId(id);
  }
  
  public boolean login(String email, String password){
    boolean loggedIn = false;
    ResponseEntity<LoginUser> response = new ResponseEntity<LoginUser>(loginUserService.getLoginUserByEmail(email),HttpStatus.OK);
    
    if(response.getBody().getPassword().equals(password) && response.getBody().getDateDeleted() == null) {
      currentLogin = new CurrentLogin(response.getBody().getLoginUserId(), response.getBody().getEmail(), response.getBody().getPassword());
      srv.setCurrentLogin(currentLogin);
      loggedIn = true;
    }
    return loggedIn;
  }
  
  public void logout() {
    currentLogin = null;
    srv.setCurrentLogin(null);
  }
  
  public ResponseEntity<LoginUser> createLU (LoginUser loginUser) throws DuplicateFoundException {
    //if already logged in then log other user out first
    logout();
    ResponseEntity<LoginUser> response = new ResponseEntity<LoginUser>(loginUserService.createLoginUser(loginUser),HttpStatus.CREATED);
    if(response.getBody() == null) {
      throw new DuplicateFoundException("LoginUser", "email", loginUser.getEmail());}
    currentLogin = new CurrentLogin(response.getBody().getLoginUserId(), response.getBody().getEmail(), response.getBody().getPassword());
    srv.setCurrentLogin(currentLogin);
    return response;
  }
  
  public ResponseEntity<LoginUser> updateLU (@RequestBody LoginUser loginUser) throws DuplicateFoundException{
    getCurrentLU();
    ResponseEntity<LoginUser> response = new ResponseEntity<LoginUser>(loginUserService.updateLoginUser(loginUser, currentLogin.getLoginUserId(), currentLogin), HttpStatus.OK);
    if(response.getBody() == null) {
      throw new DuplicateFoundException("LoginUser", "email", loginUser.getEmail());
    }
    currentLogin.setEmail(response.getBody().getEmail());
    currentLogin.setPassword(response.getBody().getPassword());
    srv.setCurrentLogin(currentLogin);
    return response;
  }
  
  public String deleteLU() {
    getCurrentLU();
    loginUserService.deleteLoginUserById(currentLogin.getLoginUserId(), currentLogin);
    logout();
    return "LoginUser has been date deleted";
  }
  
}
