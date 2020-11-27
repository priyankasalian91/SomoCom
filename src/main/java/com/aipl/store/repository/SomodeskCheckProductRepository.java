package com.aipl.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipl.store.domain.SomodeskCheckProduct;

@Repository
public interface SomodeskCheckProductRepository extends JpaRepository<SomodeskCheckProduct, Long> {

}
