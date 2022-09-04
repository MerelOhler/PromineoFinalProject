package com.shortredvan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;

public interface PartyRepository extends JpaRepository<Party, Integer> {
  //@formatter:off
  @Query (value = "SELECT P.* "
      + "FROM PartyLoginUser PLU "
      + "INNER JOIN Party P ON P.PartyId = PLU.PartyId "
      + "WHERE PLU.LoginUserId = :loginUserId"
      , nativeQuery = true)
  public List<Party> findByLU (int loginUserId);
//@formatter:on



}
