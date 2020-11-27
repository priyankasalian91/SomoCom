package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipl.store.domain.SomodeskInviteUser;
import com.aipl.store.repository.SomodeskTest2Repository;
import com.aipl.store.service.SomodeskTest2Service;

@Service
public class SomodeskTest2ServiceImpl  implements SomodeskTest2Service {

	
	@Autowired
	SomodeskTest2Repository somodeskTest2Repository;
	@Override
	public SomodeskInviteUser save(SomodeskInviteUser details) {
		return somodeskTest2Repository.save(details);
	}

}
