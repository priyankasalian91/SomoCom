package com.aipl.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.aipl.store.domain.Article;
import com.aipl.store.domain.BulkPurchase;

public interface BulkPurchaseRepository extends CrudRepository<BulkPurchase, Long>{
	

	// void saveBulkPurchase(Integer bulkquantity, String email ,Article article, Long userid , boolean mailsent);
	
	// void save(BulkPurchase bulkPurchase);
}
