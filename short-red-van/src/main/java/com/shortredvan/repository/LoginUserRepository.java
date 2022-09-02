package com.shortredvan.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shortredvan.entity.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, Integer>{
  public Optional<LoginUser> findByEmail(String email);

}
