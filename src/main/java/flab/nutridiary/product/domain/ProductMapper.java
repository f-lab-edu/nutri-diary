package flab.nutridiary.product.domain;

import flab.nutridiary.product.dto.NewProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product from(NewProductRequest productRequest) {
        return Product.builder()
                .productName(productRequest.getProductName())
                .productCorp(productRequest.getCorpName())
                .nutritionFacts(getNutritionFacts(productRequest))
                .build();
    }

    private static NutritionFacts getNutritionFacts(NewProductRequest productRequest) {
        return NutritionFacts.builder()
                .productCalories(productRequest.getCalories())
                .productCarbohydrate(productRequest.getCarbohydrate())
                .productProtein(productRequest.getProtein())
                .productFat(productRequest.getFat())
                .productServingUnit(productRequest.getServingUnit())
                .productServingWeightGram(productRequest.getServingWeightGram())
                .build();
    }
}
