package flab.nutridiary.product.dto;

import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerGram;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.constant.BigDecimalConst;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class NewProductRequest {
    private String productName;
    private String corpName;
    private int servingSize;
    private String servingUnit;
    private int servingWeightGram;
    private BigDecimal calories;
    private BigDecimal carbohydrate;
    private BigDecimal protein;
    private BigDecimal fat;

    public Product toProduct() {
        return Product.builder()
                .productName(productName)
                .productCorp(corpName)
                .nutritionFacts(getNutritionFacts())
                .nutritionFactsPerGram(getNutritionFactsPerGram())
                .build();
    }

    private NutritionFacts getNutritionFacts() {
        return NutritionFacts.builder()
                .calories(calories)
                .carbohydrate(carbohydrate)
                .protein(protein)
                .fat(fat)
                .servingUnit(servingUnit)
                .servingWeightGram(servingWeightGram)
                .build();
    }

    private NutritionFactsPerGram getNutritionFactsPerGram() {
        return NutritionFactsPerGram.builder()
                .calories(calories.divide(BigDecimal.valueOf(servingWeightGram), BigDecimalConst.SCALE, BigDecimalConst.ROUNDING_MODE))
                .carbohydrate(carbohydrate.divide(BigDecimal.valueOf(servingWeightGram), BigDecimalConst.SCALE, BigDecimalConst.ROUNDING_MODE))
                .protein(protein.divide(BigDecimal.valueOf(servingWeightGram), BigDecimalConst.SCALE, BigDecimalConst.ROUNDING_MODE))
                .fat(fat.divide(BigDecimal.valueOf(servingWeightGram), BigDecimalConst.SCALE, BigDecimalConst.ROUNDING_MODE))
                .build();
    }
}
