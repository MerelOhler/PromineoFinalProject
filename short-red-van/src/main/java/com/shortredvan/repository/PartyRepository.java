package com.shortredvan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shortredvan.entity.Party;

public interface PartyRepository extends JpaRepository<Party, Integer>{

}
