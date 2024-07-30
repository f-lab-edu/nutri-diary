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
                .productTotalCalories(productRequest.getCalories())
                .productTotalCarbohydrate(productRequest.getCarbohydrate())
                .productTotalProtein(productRequest.getProtein())
                .productTotalFat(productRequest.getFat())
                .productServingSize(productRequest.getServingSize())
                .productServingUnit(productRequest.getServingUnit())
                .productTotalWeightGram(productRequest.getTotalWeightGram())
                .build();
    }
}
