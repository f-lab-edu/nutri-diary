package flab.nutridiary.review.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.review.dto.request.CreateReviewRequest;
import flab.nutridiary.review.dto.response.CreateReviewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ReviewResisterServiceTest {
    @Autowired
    private ReviewResisterService reviewResisterService;

    @DisplayName("리뷰를 등록한다.")
    @Test
    void create() throws Exception {
        // given
        Long memberId = 1L;
        CreateReviewRequest request = CreateReviewRequest.builder()
                .productId(1L)
                .content("맛있어요")
                .dietTagId(1L)
                .storeId(1L)
                .image(null)
                .rating((short) 5)
                .build();

        // when
        CreateReviewResponse createReviewResponse = reviewResisterService.create(memberId, request);

        // then
        assertNotNull(createReviewResponse.getReviewId());
    }

    @DisplayName("리뷰를 등록할 때 이미 리뷰가 존재하는 경우 예외를 발생시킨다.")
    @Test
    void ex() throws Exception {
        //given
        Long memberId = 1L;
        CreateReviewRequest request = CreateReviewRequest.builder()
                .productId(1L)
                .content("맛있어요")
                .dietTagId(1L)
                .storeId(1L)
                .image(null)
                .rating((short) 5)
                .build();
        reviewResisterService.create(memberId, request);

        //when then
        BusinessException businessException = assertThrows(BusinessException.class, () -> reviewResisterService.create(memberId, request));
        assertEquals("이미 등록된 리뷰입니다.", businessException.getMessage());
    }
}