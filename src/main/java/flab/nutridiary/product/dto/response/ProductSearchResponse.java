package flab.nutridiary.product.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class ProductSearchResponse {
    private Long productId;
    private String productName;
    private String productCorp;
    private Long reviewCount;
    private List<String> top3_diet_tag_names;

    public ProductSearchResponse(Long productId, String productName, String productCorp, Long reviewCount , List<String> top3_diet_tag_names) {
        this.productId = productId;
        this.productName = productName;
        this.productCorp = productCorp;
        this.reviewCount = reviewCount;
        this.top3_diet_tag_names = top3_diet_tag_names;
    }
}