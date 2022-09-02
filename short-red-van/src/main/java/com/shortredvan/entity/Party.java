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

@Data
@Entity
@Table(name = "Party")
public class Party {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private int PartyId;
    
    @Column(name = "Name", unique = true)
    private String name;
    @Column(name = "AdminUserId")
    private int adminUserId;
    @Column(name = "DateCreated")
    @Setter(value = AccessLevel.NONE)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Timestamp dateCreated= new Timestamp(System.currentTimeMillis());
    @Column(name = "CreatedBy")
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Integer createdBy;
    @Column(name = "DateModified")
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Timestamp dateModified;
    @Column(name = "ModifiedBy")
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Integer modifiedBy;
    

}
