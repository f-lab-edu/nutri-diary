package flab.nutridiary.review.repository;

import flab.nutridiary.review.domain.Review;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewCrudRepository extends CrudRepository<Review, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    @Query("SELECT product_id, COUNT(*) AS review_count FROM review WHERE product_id IN (:productIds) GROUP BY product_id")
    List<ProductReviewCount> countReviewsByProductIds(@Param("productIds") List<Long> productIds);

}
