package com.eatza.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ReviewRequestDto {
	
	private String reviewDetails;
	private Long rating;
	private Long customerId;
	private Long orderId;
	private Long restaurantId;
	
	

}
