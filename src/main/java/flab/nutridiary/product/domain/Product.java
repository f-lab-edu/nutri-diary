package flab.nutridiary.product.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@ToString
public class Product {
    @Id
    private Long id;

    private String productName;

    private String productCorp;

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
    }
}

