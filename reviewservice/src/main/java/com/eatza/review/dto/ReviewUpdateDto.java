package com.eatza.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ReviewUpdateDto {
	
	private String reviewDetails;
	private Long rating;
	private Long customerId;
	
	public ReviewUpdateDto(String reviewDetails, Long rating, Long customerId) {
		super();
		this.reviewDetails = reviewDetails;
		this.rating = rating;
		this.customerId = customerId;
	}
	

		
}
