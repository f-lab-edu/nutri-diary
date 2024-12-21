package flab.nutridiary.review.repository;

import flab.nutridiary.review.domain.Review;

import java.util.List;

public interface ReviewRepository {
    Review save(Review review);
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
    List<ProductReviewCount> countReviewsByProductIds(List<Long> productIds);
}
