package com.eatza.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatza.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Optional<Review> findByCustomerId(Long customerId);

}
