package flab.nutridiary.commom.generic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Getter
@EqualsAndHashCode
@ToString
public class Nutrition {
    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private static final Nutrition ZERO = Nutrition.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    private final BigDecimal calories;
    private final BigDecimal carbohydrate;
    private final BigDecimal protein;
    private final BigDecimal fat;

    @Builder
    private Nutrition(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat) {
        this.calories = stripIfNecessary(calories);
        this.carbohydrate = stripIfNecessary(carbohydrate);
        this.protein = stripIfNecessary(protein);
        this.fat = stripIfNecessary(fat);
    }

    public Nutrition add(Nutrition nutrition) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.add(nutrition.calories))).
                carbohydrate(stripIfNecessary(this.carbohydrate.add(nutrition.carbohydrate))).
                protein(stripIfNecessary(this.protein.add(nutrition.protein))).
                fat(stripIfNecessary(this.fat.add(nutrition.fat))).
                build();
    }

    public Nutrition multiply(BigDecimal amount) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.multiply(amount))).
                carbohydrate(stripIfNecessary(this.carbohydrate.multiply(amount))).
                protein(stripIfNecessary(this.protein.multiply(amount))).
                fat(stripIfNecessary(this.fat.multiply(amount))).
                build();
    }

    public Nutrition divide(BigDecimal amount) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.divide(amount, SCALE, ROUNDING_MODE))).
                carbohydrate(stripIfNecessary(this.carbohydrate.divide(amount, SCALE, ROUNDING_MODE))).
                protein(stripIfNecessary(this.protein.divide(amount, SCALE, ROUNDING_MODE))).
                fat(stripIfNecessary(this.fat.divide(amount, SCALE, ROUNDING_MODE))).
                build();
    }

    public Nutrition subtract(Nutrition nutrition) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.subtract(nutrition.calories))).
                carbohydrate(stripIfNecessary(this.carbohydrate.subtract(nutrition.carbohydrate))).
                protein(stripIfNecessary(this.protein.subtract(nutrition.protein))).
                fat(stripIfNecessary(this.fat.subtract(nutrition.fat))).
                build();
    }

    private BigDecimal stripIfNecessary(BigDecimal value) {
        BigDecimal scaledValue = value.setScale(SCALE, ROUNDING_MODE);
        BigDecimal strippedValue = scaledValue.stripTrailingZeros();
        return strippedValue.scale() <= 0 ? strippedValue.setScale(0) : strippedValue;
    }

    public static Nutrition of(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat) {
        return Nutrition.builder().
                calories(calories).
                carbohydrate(carbohydrate).
                protein(protein).
                fat(fat).
                build();
    }
}
