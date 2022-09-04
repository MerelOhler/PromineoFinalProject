package com.shortredvan.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Table(name = "PartyLoginUserDeleted")
public class PartyLoginUserDeleted {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(accessMode = AccessMode.READ_ONLY)
  @Column(name = "PartyLoginUserDeletedId")
  private int partyLoginUserDeletedId;
  
  @Column(name = "PartyId")
  private int partyId;
  @Column(name = "LoginUserId")
  private int loginUserId;
  @Column(name = "DateCreated")
  private Timestamp dateCreated;
  @Column(name = "CreatedBy")
  private int CreatedBy;
  @Column(name = "DateDeleted")
  private Timestamp dateDeleted;
  @Column(name = "DeletedBy")
  private int deletedBy;
   
  
}
