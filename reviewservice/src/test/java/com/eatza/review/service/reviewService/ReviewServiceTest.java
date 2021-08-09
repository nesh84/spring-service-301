package com.eatza.review.service.reviewService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.eatza.review.dto.ReviewRequestDto;
import com.eatza.review.dto.ReviewUpdateDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;
import com.eatza.review.repository.ReviewRepository;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReviewServiceTest {


	@Mock
	ReviewRepository reviewRepository;

	@InjectMocks
	private ReviewServiceImpl reviewService;


	@Test
	public void viewReviewRating_basic() {
		Optional<Review> review = Optional.of(new Review("Good", 4L, 1L, 1L, 2L));
		when(reviewRepository.findById(anyLong())).thenReturn(review);
		Optional<Review> reviewReturned = reviewService.viewReviewRating(1L);
		assertEquals("Good", reviewReturned.get().getReviewDetails());
	}

//	@Test(expected = NoSuchElementException.class)
//	public void viewReviewRating_negative() throws Exception {
//
//		Optional<Review> review = Optional.empty();
//		Optional<Review> response = reviewService.viewReviewRating(review.get().getId());
//	}

	@Test
	public void provideReview_basic(){
		ReviewRequestDto reviewRequest = new ReviewRequestDto();
		reviewRequest.setCustomerId(1L);
		reviewRequest.setOrderId(1L);
		reviewRequest.setRating(3L);
		reviewRequest.setRestaurantId(1L);
		reviewRequest.setReviewDetails("Average");;

		Review savedRecord = new Review(reviewRequest.getReviewDetails(), reviewRequest.getRating(), reviewRequest.getCustomerId(), reviewRequest.getOrderId(), reviewRequest.getRestaurantId());
		savedRecord.setId(1L);

		when(reviewRepository.save(any(Review.class))).thenReturn(savedRecord);

		Review review = reviewService.provideReview(reviewRequest);
		assertNotNull(review);

	}

	@Test
	public void updateReview_basic() throws ReviewException {
		ReviewUpdateDto reviewUpdateRequest = new ReviewUpdateDto("Average", 3L, 1L);

		Optional<Review> review = Optional.of(new Review("Good", 4L, 1L, 1L, 2L));
		when(reviewRepository.findByCustomerId(anyLong())).thenReturn(review);
		review.get().setId(2L);

		Review updatedReview = new Review(reviewUpdateRequest.getReviewDetails(), reviewUpdateRequest.getRating(), reviewUpdateRequest.getCustomerId(), review.get().getOrderId(), review.get().getRestaurantId());
		updatedReview.setId(review.get().getId());


		when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);

		Review resultReview = reviewService.updateReview(reviewUpdateRequest);
		assertEquals("Average", resultReview.getReviewDetails());
	}

}
