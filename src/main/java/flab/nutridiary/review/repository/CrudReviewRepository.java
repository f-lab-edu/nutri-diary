package flab.nutridiary.review.repository;

import flab.nutridiary.review.domain.Review;
import org.springframework.data.repository.CrudRepository;

public interface CrudReviewRepository extends CrudRepository<Review, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
