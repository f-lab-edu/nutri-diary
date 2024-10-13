package flab.nutridiary.review.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.review.dto.request.CreateReviewRequest;
import flab.nutridiary.review.dto.response.CreateReviewResponse;
import flab.nutridiary.review.service.ReviewResisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewResisterService reviewResisterService;

    @PostMapping("review/new")
    public ApiResponse<CreateReviewResponse> createReview(@ModelAttribute @Valid CreateReviewRequest createReviewRequest) {
        Long memberId = 1L;
        return ApiResponse.success(reviewResisterService.create(memberId, createReviewRequest));
    }
}
