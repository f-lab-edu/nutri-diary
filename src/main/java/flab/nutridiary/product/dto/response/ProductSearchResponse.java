package flab.nutridiary.product.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode
@ToString
public class ProductSearchResponse {
    private Long productId;
    private String productName;
    private String productCorp;
    private Integer reviewCount;
    private BigDecimal reviewAvgRating;
    private String top3_diet_tag_names;

    public ProductSearchResponse(Long productId, String productName, String productCorp, Integer reviewCount, BigDecimal reviewAvgRating, String top3_diet_tag_names) {
        this.productId = productId;
        this.productName = productName;
        this.productCorp = productCorp;
        this.reviewCount = reviewCount;
        this.reviewAvgRating = reviewAvgRating == null ? BigDecimal.ZERO : reviewAvgRating.setScale(1, RoundingMode.HALF_UP);
        this.top3_diet_tag_names = top3_diet_tag_names;
    }
}