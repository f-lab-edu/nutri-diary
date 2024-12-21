package flab.nutridiary.productDietTag;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductDietTagDto {
    private Long productId;
    private String dietPlan;
    private Long tag_count;

    public ProductDietTagDto(Long productId, String dietPlan, Long tag_count) {
        this.productId = productId;
        this.dietPlan = dietPlan;
        this.tag_count = tag_count;
    }
}
