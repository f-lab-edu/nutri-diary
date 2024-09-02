package flab.nutridiary.product.domain;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.commom.generic.Nutrition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

import static flab.nutridiary.commom.exception.StatusConst.NOT_ALLOWED_SERVING_UNIT;

@ToString
@Getter
@NoArgsConstructor
public class NutritionFacts {

    private Nutrition nutritionPerOneServingUnit;

    private List<ServingUnit> allowedProductServingUnits;

    @Builder
    public NutritionFacts(Nutrition nutritionPerOneServingUnit, List<ServingUnit> allowedProductServingUnits) {
        this.nutritionPerOneServingUnit = nutritionPerOneServingUnit;
        this.allowedProductServingUnits = allowedProductServingUnits;
    }

    public Nutrition calculate(String clientChoiceServingUnitDescription, BigDecimal quantity) {
        for (ServingUnit productServingUnit : allowedProductServingUnits) {
            if (productServingUnit.isSupport(clientChoiceServingUnitDescription)) {
                BigDecimal ratioFactor = productServingUnit.getUnitConversionRate();
                return nutritionPerOneServingUnit.multiply(ratioFactor).multiply(quantity);
            }
        }
        throw new BusinessException(NOT_ALLOWED_SERVING_UNIT);
    }
}
