package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aipl.store.domain.Feedback;
import com.aipl.store.repository.FeedbackRepository;
import com.aipl.store.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Override
	public void save(Feedback feedback) {
		feedbackRepository.save(feedback);
	}

	
	
}
