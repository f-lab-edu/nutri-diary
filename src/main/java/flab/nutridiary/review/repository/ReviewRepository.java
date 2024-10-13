package flab.nutridiary.review.repository;

import flab.nutridiary.review.domain.Review;

public interface ReviewRepository {
    Review save(Review review);
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
