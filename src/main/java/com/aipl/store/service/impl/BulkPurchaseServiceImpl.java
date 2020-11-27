package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aipl.store.domain.Article;
import com.aipl.store.domain.BulkPurchase;
import com.aipl.store.repository.BulkPurchaseRepository;
import com.aipl.store.service.BulkPurchaseService;

@Service
public class BulkPurchaseServiceImpl implements BulkPurchaseService {
	
	@Autowired
	private BulkPurchaseRepository bulkPurchaseRepository;
	
	/*@Override
	public void saveBulkPurchase(Integer bulkquantity, String email ,Article article, Long userid , boolean mailsent) {
		bulkPurchaseRepository.saveBulkPurchase(bulkquantity, email ,article, userid , mailsent);
	}*/

	@Override
	public void save(BulkPurchase bulkPurchase) {
		bulkPurchaseRepository.save(bulkPurchase);
	}
	
}
