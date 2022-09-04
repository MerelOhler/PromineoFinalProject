package com.shortredvan.repository;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserKey;

public interface PartyLoginUserRepository extends JpaRepository<PartyLoginUser, PartyLoginUserKey> {
  
  @Query(value = "SELECT * "
      + "FROM PartyLoginUser "
      + "WHERE PartyId = :partyId"
      , nativeQuery = true)
  List<PartyLoginUser> findByPartyid (int partyId);
  
  @Query(value = "SELECT * "
      + "FROM PartyLoginUser "
      + "WHERE LoginUserId = :loginUserId"
      , nativeQuery = true)
  List<PartyLoginUser> findByLUid (int loginUserId);

  
}
