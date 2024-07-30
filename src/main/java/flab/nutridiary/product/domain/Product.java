package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@ToString
public class Product {

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String EMPTY = "";

    @Id
    private Long id;

    private String productName;

    private String productCorp;

    private String productNormalizedName;

    private NutritionFacts nutritionFacts;

    private Long memberId = 1L;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

    @Builder
    public Product(String productName, String productCorp, NutritionFacts nutritionFacts) {
        this.productName = productName;
        this.productCorp = productCorp;
        this.nutritionFacts = nutritionFacts;
        this.productNormalizedName = initiateNormalizedName(productName, productCorp);
    }

    private String initiateNormalizedName(String productName, String productCorp) {
        return productCorp.replaceAll(WHITESPACE_REGEX, EMPTY) + productName.replaceAll(WHITESPACE_REGEX, EMPTY);
    }
}

