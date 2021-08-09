package com.eatza.review.service.reviewService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eatza.kafka.feedback.Feedback;

@Service
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "feedbackRecived";
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	//private KafkaTemplate<String,String> kafkaTemplate;
	 
	// For String pass param as String rather than Object
	public void saveCreateUserLog(Feedback gotFeedback) 
	{
	    logger.info(String.format("Feedback created -> %s", gotFeedback));
	    this.kafkaTemplate.send(TOPIC, gotFeedback);
	}
}
