package com.aipl.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipl.store.domain.SomodeskInviteUser;
import com.aipl.store.domain.SomodeskProductLinkDetails;

@Repository
public interface SomodeskTest2Repository  extends JpaRepository<SomodeskInviteUser, Long>{

}
