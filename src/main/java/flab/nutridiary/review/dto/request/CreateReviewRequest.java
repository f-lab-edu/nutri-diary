package flab.nutridiary.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Getter
public class CreateReviewRequest {
    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long productId;

    @NotNull(message = "리뷰 내용을 입력해주세요.")
    private String content;

    @NotNull(message = "식단 태그 ID를 입력해주세요.")
    private List<Long> dietTagId;

    @NotNull(message = "매장 ID를 입력해주세요.")
    private Long storeId;

    private MultipartFile image;

    @Max(value = 5, message = "평점은 5 이하이어야 합니다.")
    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @NotNull(message = "평점을 입력해주세요.")
    private short rating;

    @Builder
    public CreateReviewRequest(Long productId, String content, List<Long> dietTagId, Long storeId, MultipartFile image, short rating) {
        this.productId = productId;
        this.content = content;
        this.dietTagId = dietTagId;
        this.storeId = storeId;
        this.image = image;
        this.rating = rating;
    }
}
