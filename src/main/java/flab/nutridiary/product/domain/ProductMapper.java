package flab.nutridiary.product.domain;

import flab.nutridiary.product.dto.request.NewProductRequest;
import flab.nutridiary.search.ProductDocument;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static flab.nutridiary.commom.generic.Nutrition.*;

@Component
public class ProductMapper {

    public Product from(NewProductRequest productRequest) {
        return Product.builder()
                .productName(productRequest.getProductName())
                .productCorp(productRequest.getCorpName())
                .nutritionFacts(getNutritionFacts(productRequest))
                .build();
    }

    public ProductDocument toDocument(Long productId, Product product) {
        return new ProductDocument(productId, product.getProductName(), product.getProductCorp());
    }

    private static NutritionFacts getNutritionFacts(NewProductRequest productRequest) {
        BigDecimal productTotalCalories = productRequest.getCalories();
        BigDecimal productTotalCarbohydrate = productRequest.getCarbohydrate();
        BigDecimal productTotalProtein = productRequest.getProtein();
        BigDecimal productTotalFat = productRequest.getFat();
        BigDecimal productDefaultServingSize = productRequest.getProductDefaultServingSize();
        BigDecimal productTotalWeightGram = productRequest.getProductTotalWeightGram();
        String productDefaultServingUnit = productRequest.getProductDefaultServingUnit();
        return NutritionFacts.builder()
                .nutritionPerOneServingUnit(of(
                            productTotalCalories.divide(productDefaultServingSize, SCALE, ROUNDING_MODE),
                            productTotalCarbohydrate.divide(productDefaultServingSize, SCALE, ROUNDING_MODE),
                            productTotalProtein.divide(productDefaultServingSize, SCALE, ROUNDING_MODE),
                            productTotalFat.divide(productDefaultServingSize, SCALE, ROUNDING_MODE)))
                .allowedProductServingUnits(
                        new ArrayList<>(List.of(
                                ServingUnit.asOneServingUnit(productDefaultServingUnit),
                                ServingUnit.ofGram(productDefaultServingSize, productTotalWeightGram))))
                .build();
    }
}
