package flab.nutridiary.commom.generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NutritionTest {

    @DisplayName("Nutrition 객체 생성 테스트")
    @Test
    void nutritionOf() {
        Nutrition nutrition = Nutrition.of(100, 50, 30, 20);
        assertAll(
                () -> assertEquals(new BigDecimal("100"), nutrition.getCalories()),
                () -> assertEquals(new BigDecimal("50"), nutrition.getCarbohydrate()),
                () -> assertEquals(new BigDecimal("30"), nutrition.getProtein()),
                () -> assertEquals(new BigDecimal("20"), nutrition.getFat())
        );
    }

    @DisplayName("Nutrition 객체 덧셈 테스트")
    @Test
    void add() {
        Nutrition base = Nutrition.of(101.99, 57, 3.1, 21.12);
        Nutrition addition = Nutrition.of(50.4, 25.7, 15.9, 10.08);
        Nutrition result = base.add(addition);
        assertAll(
                () -> assertEquals(new BigDecimal("152.39"), result.getCalories()),
                () -> assertEquals(new BigDecimal("82.7"), result.getCarbohydrate()),
                () -> assertEquals(new BigDecimal("19"), result.getProtein()),
                () -> assertEquals(new BigDecimal("31.2"), result.getFat())
        );
    }

    @DisplayName("Nutrition 객체 뺄셈 테스트")
    @Test
    void subtract() {
        Nutrition base = Nutrition.of(200.2, 100.3, 60.1, 40.3);
        Nutrition subtraction = Nutrition.of(50.34, 25.99, 15, 10.77);
        Nutrition result = base.subtract(subtraction);
        assertAll(
                () -> assertEquals(new BigDecimal("149.86"), result.getCalories()),
                () -> assertEquals(new BigDecimal("74.31"), result.getCarbohydrate()),
                () -> assertEquals(new BigDecimal("45.1"), result.getProtein()),
                () -> assertEquals(new BigDecimal("29.53"), result.getFat())
        );
    }

    @DisplayName("Nutrition 객체 곱셈 테스트")
    @Test
    void multiply() {
        Nutrition base = Nutrition.of(100.77, 51.5, 30.99, 20.3);
        Nutrition result = base.multiply(10.3);
        assertAll(
                () -> assertEquals(new BigDecimal("1037.93"), result.getCalories()),
                () -> assertEquals(new BigDecimal("530.45"), result.getCarbohydrate()),
                () -> assertEquals(new BigDecimal("319.2"), result.getProtein()),
                () -> assertEquals(new BigDecimal("209.09"), result.getFat())
        );
    }

    @DisplayName("Nutrition 객체 나눗셈 테스트")
    @Test
    void divide() {
        Nutrition base = Nutrition.of(100.55, 50.1, 30.05, 110);
        Nutrition result = base.divide(2.2);
        assertAll(
                () -> assertEquals(new BigDecimal("45.7"), result.getCalories()),
                () -> assertEquals(new BigDecimal("22.77"), result.getCarbohydrate()),
                () -> assertEquals(new BigDecimal("13.66"), result.getProtein()),
                () -> assertEquals(new BigDecimal("50"), result.getFat())
        );
    }

    @DisplayName("Nutrition 객체 동등성 테스트")
    @Test
    void testEqualityAndHashCode() {
        Nutrition n1 = Nutrition.of(100, 50, 30, 20);
        Nutrition n2 = Nutrition.of(100, 50, 30, 20);
        assertAll(
                () -> assertEquals(n1, n2),
                () -> assertEquals(n1.hashCode(), n2.hashCode())
        );
    }
}
