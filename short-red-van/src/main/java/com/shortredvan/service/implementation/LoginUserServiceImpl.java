package com.shortredvan.service.implementation;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.shortredvan.entity.CurrentLogin;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.exception.DuplicateFoundException;
import com.shortredvan.exception.ResourceNotFoundException;
import com.shortredvan.repository.LoginUserRepository;
import com.shortredvan.service.LoginUserService;

@Service
public class LoginUserServiceImpl implements LoginUserService {
  
  private LoginUserRepository loginUserRepository;
  
  @Autowired
  public LoginUserServiceImpl(LoginUserRepository loginUserRepository) {
    this.loginUserRepository = loginUserRepository;
  }

  @Override
  public LoginUser getLoginUserById(int id) {
    return loginUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LoginUser","ID", id));
  }

  @Override
  public List<LoginUser> getAllLoginUsers() {
    return loginUserRepository.findAll();
  }

  @Override
  public LoginUser getLoginUserByEmail(String email) {
    return loginUserRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("LoginUser","Email",email));
  }

  @Override
  public LoginUser createLoginUser(LoginUser loginUser) {
    try {
      return loginUserRepository.save(loginUser);
    } catch (DataIntegrityViolationException e) {
      new DuplicateFoundException("LoginUser", "Email", loginUser.getEmail());
      return null;
    }
  }

  @Override
  public LoginUser updateLoginUser(LoginUser loginUser, int id, CurrentLogin currentLogin) {
    try {
      LoginUser existingLU = loginUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LoginUserID","ID", id));
      existingLU.setEmail(loginUser.getEmail());
      existingLU.setFirstName(loginUser.getFirstName());
      existingLU.setLastName(loginUser.getLastName());
      existingLU.setPassword(loginUser.getPassword());
      existingLU.setUserRole(loginUser.getUserRole());
      //cannot update date created
      existingLU.setDateModified(new Timestamp(System.currentTimeMillis()));
      existingLU.setModifiedBy(currentLogin.getLoginUserId());
      return loginUserRepository.save(existingLU);
    } catch (DataIntegrityViolationException e) {
      new DuplicateFoundException("LoginUser", "Email", loginUser.getEmail());
      return null;
    }
  }

  @Override
  public void deleteLoginUserById(int id, CurrentLogin currentLogin) {
    LoginUser existingLU = loginUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LoginUserID","ID", id));
    existingLU.setModifiedBy(currentLogin.getLoginUserId());
    loginUserRepository.save(existingLU);
    loginUserRepository.deleteById(id);
  }


}
