package com.shortredvan.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Entity
@Table(name = "Message")
@Data
public class Message {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private int messageId;
  
  @Column(name = "MessageText")
  private String messageText;
  
  @Column(name = "ParentMessageId")
  private Integer parentMessageId;
  
  @Column(name = "PartyId")
  private int partyId;
  
  @Column(name = "DateCreated")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());
  
  @Column(name = "CreatedBy")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private int createdBy;
  
  @Column(name = "DateModified")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Timestamp dateModified;
  
  @Column(name = "ModifiedBy")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Integer modifiedBy;
  
  @Column(name = "DateDeleted")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Timestamp dateDeleted;
  
  @Column(name = "DeletedBy")
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Integer deletedBy;
}
