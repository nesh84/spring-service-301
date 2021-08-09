package com.eatza.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.review.dto.ReviewRequestDto;
import com.eatza.review.dto.ReviewUpdateDto;
import com.eatza.review.model.Review;
import com.eatza.review.service.reviewService.ReviewServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value= ReviewController.class)
public class ReviewControllerTest {

	@MockBean
	ReviewServiceImpl reviewService;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void provideReview() throws Exception {
		ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
		reviewRequestDto.setCustomerId(1L);
		reviewRequestDto.setRestaurantId(1L);
		reviewRequestDto.setOrderId(1L);
		reviewRequestDto.setRating(3L);
		reviewRequestDto.setReviewDetails("Average");
		
		Review review = new Review(reviewRequestDto.getReviewDetails(), reviewRequestDto.getRating(), reviewRequestDto.getCustomerId(), reviewRequestDto.getOrderId(), reviewRequestDto.getRestaurantId());
		when(reviewService.provideReview(any(ReviewRequestDto.class))).thenReturn(review);
		review.setId(1L);
		RequestBuilder request = MockMvcRequestBuilders.post(
				"/review")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((reviewRequestDto)));
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
	}
	
	
	@Test
	public void updateReview() throws Exception{
		
		ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto("Bad", 2L, 2L);
		Review updatedRecord = new Review("Better", 4L, 2L, 1L, 1L);
		updatedRecord.setId(1L);
		when(reviewService.updateReview(any(ReviewUpdateDto.class))).thenReturn(updatedRecord);
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/order/update")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString((reviewUpdateDto)));
		mockMvc.perform(request)
		.andExpect(status().is(404))
		.andReturn();

	}
	
	@Test
	public void getReviewById() throws Exception {

		Optional<Review> review = Optional.of(new Review("Good", 3L, 1L, 1L, 1L));
		review.get().setId(1L);
		when(reviewService.viewReviewRating(anyLong())).thenReturn(review);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/review/1")
				.accept(MediaType.ALL);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();

	}

	@Test
	public void getReviewById_negative() throws Exception {

		Optional<Review> review = Optional.empty();
		when(reviewService.viewReviewRating(anyLong())).thenReturn(review);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/review/1")
				.accept(MediaType.ALL);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}

}
