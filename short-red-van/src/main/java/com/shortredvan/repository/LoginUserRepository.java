package com.shortredvan.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;

public interface LoginUserRepository extends JpaRepository<LoginUser, Integer>{
  public Optional<LoginUser> findByEmail(String email);
  
  //@formatter:off
  @Query (value = "SELECT LU.* "
      + "FROM PartyLoginUser PLU "
      + "INNER JOIN LoginUser LU ON LU.LoginUserId = PLU.LoginUserId "
      + "WHERE PLU.PartyId = :partyId"
      , nativeQuery = true)
  public List<LoginUser> findByPartyId (int partyId);
//@formatter:on

}
