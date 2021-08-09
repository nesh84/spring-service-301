package com.eatza.kafka.feedback;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class Feedback {
	
	private Long customerId;
	private String reviewDetails;
	private Long rating;
	
	@Override
	public String toString() {
		return "Thank You, we have recived your Feedback. Details: Rating - "+rating+ " Review - " +reviewDetails+" for Customer ID - "+ customerId; 
	}

	public Feedback(Long customerId, String reviewDetails, Long rating) {
		super();
		this.customerId = customerId;
		this.reviewDetails = reviewDetails;
		this.rating = rating;
	}

}
