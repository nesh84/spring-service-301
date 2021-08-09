package com.eatza.review.service.reviewService;

import java.util.Optional;

import com.eatza.review.dto.ReviewRequestDto;
import com.eatza.review.dto.ReviewUpdateDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;

public interface ReviewService  {
	
	Review provideReview(ReviewRequestDto reviewRequest);
	Optional<Review> viewReviewRating(Long reviewId);
	Review updateReview(ReviewUpdateDto updateReview) throws ReviewException;
}
