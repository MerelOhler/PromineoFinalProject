package com.shortredvan.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "PartyLoginUser")
public class PartyLoginUser {
  
  @EmbeddedId
  PartyLoginUserKey id;
  
  @Column(name = "DateCreated")
  @Setter(value = AccessLevel.NONE)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Timestamp dateCreated= new Timestamp(System.currentTimeMillis());
  @Column(name = "CreatedBy")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Integer createdBy;

  

}
