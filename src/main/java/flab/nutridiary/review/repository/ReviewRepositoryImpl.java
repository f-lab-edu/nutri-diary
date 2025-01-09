package flab.nutridiary.review.repository;

import flab.nutridiary.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewCrudRepository reviewCrudRepository;

    @Override
    public Review save(Review review) {
        return reviewCrudRepository.save(review);
    }

    @Override
    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return reviewCrudRepository.existsByMemberIdAndProductId(memberId, productId);
    }

    @Override
    public List<ProductReviewCount> countReviewsByProductIds(List<Long> productIds) {
        return reviewCrudRepository.countReviewsByProductIds(productIds);
    }
}
