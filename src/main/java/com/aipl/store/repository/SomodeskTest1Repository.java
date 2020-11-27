package com.aipl.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipl.store.domain.SomodeskProductLinkDetails;

@Repository
public interface SomodeskTest1Repository  extends JpaRepository<SomodeskProductLinkDetails, Long>{

}
