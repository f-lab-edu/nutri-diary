package flab.nutridiary.product.domain;

import flab.nutridiary.commom.generic.Nutrition;
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
                .totalNutrition(Nutrition.of(productRequest.getCalories(), productRequest.getCarbohydrate(), productRequest.getProtein(), productRequest.getFat()))
                .productServingSize(productRequest.getServingSize())
                .productServingUnit(productRequest.getServingUnit())
                .productTotalWeightGram(productRequest.getTotalWeightGram())
                .build();
    }
}
