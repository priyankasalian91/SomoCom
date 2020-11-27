package com.aipl.store.service;


import com.aipl.store.domain.BulkPurchase;

public interface BulkPurchaseService {
	
	//void saveBulkPurchase(Integer bulkquantity, String email ,Article article, Long userid , boolean mailsent);
		
	void save(BulkPurchase bulkPurchase);
	
}
