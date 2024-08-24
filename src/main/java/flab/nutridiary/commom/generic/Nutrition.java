package flab.nutridiary.commom.generic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.*;


@Getter
@EqualsAndHashCode
@ToString
public class Nutrition {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private static final Nutrition ZERO = Nutrition.of(0, 0, 0, 0);

    private final BigDecimal calories;
    private final BigDecimal carbohydrate;
    private final BigDecimal protein;
    private final BigDecimal fat;

    public static Nutrition of(long calories, long carbohydrate, long protein, long fat) {
        return Nutrition.builder().
                calories(valueOf(calories)).
                carbohydrate(valueOf(carbohydrate)).
                protein(valueOf(protein)).
                fat(valueOf(fat)).
                build();
    }

    public static Nutrition of(double calories, double carbohydrate, double protein, double fat) {
        return Nutrition.builder().
                calories(valueOf(calories)).
                carbohydrate(valueOf(carbohydrate)).
                protein(valueOf(protein)).
                fat(valueOf(fat)).
                build();
    }

    public static Nutrition of(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat) {
        return Nutrition.builder().
                calories(calories).
                carbohydrate(carbohydrate).
                protein(protein).
                fat(fat).
                build();
    }

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

    public Nutrition multiply(long amount) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.multiply(valueOf(amount)))).
                carbohydrate(stripIfNecessary(this.carbohydrate.multiply(valueOf(amount)))).
                protein(stripIfNecessary(this.protein.multiply(valueOf(amount)))).
                fat(stripIfNecessary(this.fat.multiply(valueOf(amount)))).
                build();
    }


    public Nutrition multiply(double amount) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.multiply(valueOf(amount)))).
                carbohydrate(stripIfNecessary(this.carbohydrate.multiply(valueOf(amount)))).
                protein(stripIfNecessary(this.protein.multiply(valueOf(amount)))).
                fat(stripIfNecessary(this.fat.multiply(valueOf(amount)))).
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

    public Nutrition divide(long amount) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                carbohydrate(stripIfNecessary(this.carbohydrate.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                protein(stripIfNecessary(this.protein.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                fat(stripIfNecessary(this.fat.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                build();
    }

    public Nutrition divide(double amount) {
        return Nutrition.builder().
                calories(stripIfNecessary(this.calories.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                carbohydrate(stripIfNecessary(this.carbohydrate.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                protein(stripIfNecessary(this.protein.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
                fat(stripIfNecessary(this.fat.divide(valueOf(amount), SCALE, ROUNDING_MODE))).
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
}
