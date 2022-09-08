package com.shortredvan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.shortredvan.entity.LoginUser;

@SpringBootApplication
public class ShortRedVan {
  
  LoginUser currentLogin;
  
  public static void main(String[] args) {
    SpringApplication.run(ShortRedVan.class, args);
  }
  
  public LoginUser getCurrentLogin() {
    return currentLogin;
  }
  
  public void setCurrentLogin (LoginUser currentLogin) {
    this.currentLogin = currentLogin;
  }

}
