package com.eatza.review.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.kafka.feedback.Feedback;
import com.eatza.review.dto.ReviewRequestDto;
import com.eatza.review.dto.ReviewUpdateDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;
import com.eatza.review.service.reviewService.Producer;
import com.eatza.review.service.reviewService.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService reviewService;

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	private final Producer producer;

	@Autowired
	public ReviewController(Producer producer) {
		this.producer = producer;
	}

	// For Object Kafka
	@PostMapping(value = "/feedback")
	public void sendMessageToKafkaTopic(@RequestParam("review") String review) {
		// this.producer.sendMessage(note); -- when working with String
		Feedback gotFeedback = new Feedback(1L, review, 4L);
		this.producer.saveCreateUserLog(gotFeedback);
	}

	@PostMapping
	public ResponseEntity<Review> provideReview(@RequestBody ReviewRequestDto reviewRequestDto) {
		logger.debug("In rprovide review method, calling the service");
		Review review = reviewService.provideReview(reviewRequestDto);
		logger.debug("Customer review provided");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(review);

	}

	// customer can update their rating 
	@PutMapping("/update")
	public ResponseEntity<String> updateReview(@RequestBody ReviewUpdateDto reviewUpdateDto) throws ReviewException {

		logger.debug(" In updateReview method, calling service");
		Review updatedReview = reviewService.updateReview(reviewUpdateDto);
		long reviewId = updatedReview.getId();
		if(reviewId != 0) {
			logger.debug("Review / Rating updated, please check!");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Thank You, we have recived your Feedback. ReviewId - "+reviewId); 
		} else {
			logger.debug("No records found for respective data");
			throw new ReviewException("No records found for respective data");
		}	

	}


	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) throws ReviewException{
		logger.debug("In get review by id method, calling service to get rating and review");
		Optional<Review> review = reviewService.viewReviewRating(reviewId);
		if(review.isPresent()) {
			logger.debug("Got review from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(review.get());
		}
		else {
			logger.debug("No review were found");
			throw new ReviewException("No result found for specified inputs");
		}
	}
}

