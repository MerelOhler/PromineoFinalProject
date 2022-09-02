package com.shortredvan.controller.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shortredvan.ShortRedVan;
import com.shortredvan.controller.LoginUserController;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.NotLoggedInException;
import com.shortredvan.service.LoginUserService;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/loginuser")
public class LoginUserControllerImpl implements LoginUserController{
  
  private LoginUserService loginUserService;
  private CurrentLogin currentLogin;
  private ShortRedVan srv;
  
  @Autowired
  public LoginUserControllerImpl(LoginUserService loginUserService, ShortRedVan srv) {
    this.loginUserService = loginUserService;
    this.srv = srv;
  }
  
  public List<LoginUser> getAllLU(){
    return loginUserService.getAllLoginUsers();
  }
  
  public ResponseEntity<LoginUser> getLUById(int id) {
    return new ResponseEntity<LoginUser>(loginUserService.getLoginUserById(id),HttpStatus.OK);
  }
  
  public boolean login(String email, String password){
    boolean loggedIn = false;
    ResponseEntity<LoginUser> response = new ResponseEntity<LoginUser>(loginUserService.getLoginUserByEmail(email),HttpStatus.OK);
    
    if(response.getBody().getPassword().equals(password)) {
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
    ResponseEntity<LoginUser> response = new ResponseEntity<LoginUser>(loginUserService.createLoginUser(loginUser),HttpStatus.CREATED);
    if(response.getBody() == null) {
      throw new DuplicateFoundException("LoginUser", "email", loginUser.getEmail());}
    currentLogin = new CurrentLogin(response.getBody().getLoginUserId(), response.getBody().getEmail(), response.getBody().getPassword());
    srv.setCurrentLogin(currentLogin);
    return response;
  }
  
  public ResponseEntity<LoginUser> updateLU (int id, @RequestBody LoginUser loginUser) throws DuplicateFoundException{
    if (currentLogin == null) {
      throw new NotLoggedInException();
    }
    ResponseEntity<LoginUser> response = new ResponseEntity<LoginUser>(loginUserService.updateLoginUser(loginUser, id, currentLogin), HttpStatus.OK);
    if(response.getBody() == null) {
      throw new DuplicateFoundException("LoginUser", "email", loginUser.getEmail());
    }
    currentLogin.setEmail(response.getBody().getEmail());
    currentLogin.setPassword(response.getBody().getPassword());
    srv.setCurrentLogin(currentLogin);
    return response;
  }
  
  public String deleteLU(int id) {
    if (currentLogin == null) {
      throw new NotLoggedInException();
    }
    loginUserService.deleteLoginUserById(id, currentLogin);
    return "LoginUser has been deleted";
  }


  
}
