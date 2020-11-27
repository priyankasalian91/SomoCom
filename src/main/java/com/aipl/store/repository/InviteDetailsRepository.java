package com.aipl.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipl.store.domain.InviteDetails;

@Repository
public interface InviteDetailsRepository  extends JpaRepository<InviteDetails, Long>{

}
