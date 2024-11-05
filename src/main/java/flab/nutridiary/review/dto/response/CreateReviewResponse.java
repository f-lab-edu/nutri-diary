package flab.nutridiary.review.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateReviewResponse {
    private final Long reviewId;

    public static CreateReviewResponse of(Long reviewId) {
        return new CreateReviewResponse(reviewId);
    }
}
