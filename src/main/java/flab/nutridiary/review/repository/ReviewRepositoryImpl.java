package flab.nutridiary.review.repository;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    private final CrudReviewRepository crudReviewRepository;

    @Override
    public Review save(Review review) {
        return crudReviewRepository.save(review);
    }

    @Override
    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return crudReviewRepository.existsByMemberIdAndProductId(memberId, productId);
    }
}
