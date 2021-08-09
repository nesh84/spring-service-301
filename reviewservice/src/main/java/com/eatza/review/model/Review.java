package com.eatza.review.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="review")
@Getter @Setter @NoArgsConstructor
public class Review {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String reviewDetails;
	private Long rating;
	private Long customerId;
	private Long orderId;
	private Long restaurantId;
	
	public Review(String reviewDetails, Long rating, Long customerId, Long orderId, Long restaurantId) {
		this.reviewDetails = reviewDetails;
		this.rating = rating;
		this.customerId = customerId;
		this.orderId = orderId;
		this.restaurantId = restaurantId;
	}

	
}
