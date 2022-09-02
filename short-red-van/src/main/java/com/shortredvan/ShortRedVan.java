package com.shortredvan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.shortredvan.entity.CurrentLogin;

@SpringBootApplication
public class ShortRedVan {
  
  CurrentLogin currentLogin;
  
  public static void main(String[] args) {
    SpringApplication.run(ShortRedVan.class, args);
  }
  
  public CurrentLogin getCurrentLogin() {
    return currentLogin;
  }
  
  public void setCurrentLogin (CurrentLogin currentLogin) {
    this.currentLogin = currentLogin;
  }

}
