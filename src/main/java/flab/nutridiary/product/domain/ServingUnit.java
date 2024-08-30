package flab.nutridiary.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

import static flab.nutridiary.commom.generic.Nutrition.ROUNDING_MODE;
import static flab.nutridiary.commom.generic.Nutrition.SCALE;

@EqualsAndHashCode
@ToString
@Getter
public class ServingUnit {
    public static final String GRAM = "gram";
    private String description;
    private BigDecimal unitConversionRate;

    private ServingUnit(String description, BigDecimal unitConversionRate) {
        this.description = description;
        this.unitConversionRate = unitConversionRate;
    }

    public Boolean isSupport(String servingUnitDescription) {
        return this.description.equals(servingUnitDescription);
    }

    public static ServingUnit asOneServingUnit(String description) {
        return new ServingUnit(description, BigDecimal.ONE);
    }

    public static ServingUnit ofGram(BigDecimal productDefaultServingSize, BigDecimal productTotalWeightGram) {
        return new ServingUnit(GRAM, productDefaultServingSize.divide(productTotalWeightGram, SCALE, ROUNDING_MODE));
    }
}