package flab.nutridiary.review.repository;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.review.domain.Review;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;

public interface CrudReviewRepository extends CrudRepository<Review, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, AggregateReference<Product, Long> productId);
}
