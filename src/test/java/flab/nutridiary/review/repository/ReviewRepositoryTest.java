package flab.nutridiary.review.repository;

import flab.nutridiary.review.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @DisplayName("리뷰를 저장한다.")
    @Test
    public void test() {
        // given
        Review review = Review.builder()
                .productId(1L)
                .content("맛있어요")
                .rating((short) 5)
                .build();

        // when
        Review savedReview = reviewRepository.save(review);

        // then
        assertNotNull(savedReview.getId());
        assertEquals(review.getMemberId(), savedReview.getMemberId());
        assertEquals(review.getProductId(), savedReview.getProductId());
        assertEquals(review.getContent(), savedReview.getContent());
    }

    @DisplayName("멤버 아이디와 상품 아이디로 리뷰가 존재하는지 확인한다.")
    @Test
    void existsByMemberIdAndProductId() throws Exception {
        // given
        Long memberId = 1L;
        Long productId = 1L;

        reviewRepository.save(Review.builder()
                .productId(productId)
                .content("맛있어요")
                .rating((short) 5)
                .build());

        // when
        boolean results = reviewRepository.existsByMemberIdAndProductId(memberId, productId);

        // then
        assertThat(results).isTrue();
    }
}