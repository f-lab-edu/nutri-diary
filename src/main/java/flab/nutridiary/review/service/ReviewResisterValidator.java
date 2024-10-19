package flab.nutridiary.review.service;

import flab.nutridiary.review.repository.ReviewRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewResisterValidator {
    private final ReviewRepository reviewRepository;

    public ReviewResisterValidator(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean isExists(Long memberId, Long productId) {
        return reviewRepository.existsByMemberIdAndProductId(memberId, productId);
    }
}
