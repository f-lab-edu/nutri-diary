package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@ToString
public class Product {

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String EMPTY = "";

    @Id @Column("PRODUCT_ID")
    private Long id;

    private String productName;

    private String productCorp;

    private String productNormalizedName;

    private NutritionFacts nutritionFacts;

    private Long memberId = 1L;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
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

