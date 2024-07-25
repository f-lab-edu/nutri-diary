package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Table
public class Product {
    @Id
    private Long id;

    private String productName;

    private String ProductCorp;

    @MappedCollection(idColumn = "PRODUCT_ID")
    private NutritionFacts nutritionFacts;

    @MappedCollection(idColumn = "PRODUCT_ID")
    private NutritionFactsPerGram nutritionFactsPerGram;

    @Builder
    private Product(String productName, String productCorp, NutritionFacts nutritionFacts, NutritionFactsPerGram nutritionFactsPerGram) {
        this.productName = productName;
        this.ProductCorp = productCorp;
        this.nutritionFacts = nutritionFacts;
        this.nutritionFactsPerGram = nutritionFactsPerGram;
    }
}
