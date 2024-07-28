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
    @Id
    private Long id;

    private String productName;

    private String productCorp;

    private String productNormalizedName;

    private NutritionFacts nutritionFacts;

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
        String EMPTY_REGEX = "\\s+";
        return productCorp.replaceAll(EMPTY_REGEX, "") + productName.replaceAll(EMPTY_REGEX, "");
    }
}

