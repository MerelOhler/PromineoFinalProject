package com.shortredvan.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "LoginUser")
public class LoginUser {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private int loginUserId;
  
  @Column(name = "Email", unique = true)
  private String email;
  @Column(name = "FirstName")
  private String firstName;
  @Column(name = "LastName")
  private String lastName;
  @Column(name = "Password")
  private String password;
  @Column(name = "UserRole")
  @Enumerated(EnumType.STRING)
  private UserRole userRole;
  @Column(name = "DateCreated")
  @Setter(value = AccessLevel.NONE)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Timestamp dateCreated= new Timestamp(System.currentTimeMillis());
  @Column(name = "DateModified")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Timestamp dateModified;
  @Column(name = "ModifiedBy")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Integer modifiedBy;
  

}
