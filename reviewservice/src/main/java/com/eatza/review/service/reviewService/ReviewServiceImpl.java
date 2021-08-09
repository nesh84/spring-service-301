package com.eatza.review.service.reviewService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.review.dto.ReviewRequestDto;
import com.eatza.review.dto.ReviewUpdateDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;
import com.eatza.review.repository.ReviewRepository;


@Service
public class ReviewServiceImpl implements ReviewService {


	@Autowired
	ReviewRepository reviewRepository;

	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

	@Override
	public Optional<Review> viewReviewRating(Long reviewId) {
		logger.debug("In view review / rating method, calling repo");
		return reviewRepository.findById(reviewId);
	}

	@Override
	public Review provideReview(ReviewRequestDto reviewRequest) {
		logger.debug("In provide review method, calling repo");
		Review review = new Review(reviewRequest.getReviewDetails(), reviewRequest.getRating(), reviewRequest.getCustomerId(), reviewRequest.getOrderId(), reviewRequest.getRestaurantId());
		Review saveReview = reviewRepository.save(review);
		logger.debug("Saved review / rating to db");
		return saveReview;
	}

	@Override
	public Review updateReview(ReviewUpdateDto reviewUpdateRequest) throws ReviewException {
		logger.debug("In update review method, calling repo");
		Optional<Review> previouslyPersistedData = reviewRepository.findByCustomerId(reviewUpdateRequest.getCustomerId());

		if(!previouslyPersistedData.isPresent()) {
			throw new ReviewException("Update Failed, respective review details not found");
		}
		else {

			Review review = new Review(reviewUpdateRequest.getReviewDetails(), reviewUpdateRequest.getRating(), reviewUpdateRequest.getCustomerId(), previouslyPersistedData.get().getOrderId(), previouslyPersistedData.get().getRestaurantId());
			review.setId(previouslyPersistedData.get().getId());
			Review updatedReview =	reviewRepository.save(review);
			return updatedReview;
		}
	}
}
