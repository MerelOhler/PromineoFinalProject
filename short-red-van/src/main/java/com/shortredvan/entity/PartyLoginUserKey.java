package com.shortredvan.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class PartyLoginUserKey implements Serializable{
  @Column(name = "PartyId")
  Integer partyId;
  
  @Column(name = "LoginUserId")
  Integer loginUserId;
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PartyLoginUserKey pluk = (PartyLoginUserKey) o;
      return partyId.equals(pluk.partyId) &&
              loginUserId.equals(pluk.loginUserId);
  }

  @Override
  public int hashCode() {
      return Objects.hash(partyId, loginUserId);
  }


}
