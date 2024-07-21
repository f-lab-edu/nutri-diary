package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@ToString
public class Product {
    @Id
    private Long id;

    private String productName;

    private String ProductCorp;

    private NutritionFacts nutritionFacts;

    private NutritionFactsPerGram nutritionFactsPerGram;

    @Builder
    public Product(String productName, String productCorp, NutritionFacts nutritionFacts, NutritionFactsPerGram nutritionFactsPerGram) {
        this.productName = productName;
        this.ProductCorp = productCorp;
        this.nutritionFacts = nutritionFacts;
        this.nutritionFactsPerGram = nutritionFactsPerGram;
    }
}
