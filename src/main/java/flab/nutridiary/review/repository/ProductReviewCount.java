package flab.nutridiary.review.repository;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductReviewCount {
    private Long productId;
    private Long reviewCount;

    public ProductReviewCount(Long productId, Long reviewCount) {
        this.productId = productId;
        this.reviewCount = reviewCount;
    }
}
