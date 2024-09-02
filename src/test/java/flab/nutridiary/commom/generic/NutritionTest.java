package flab.nutridiary.commom.generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NutritionTest {

    @DisplayName("Nutrition 객체 생성 테스트")
    @Test
    void nutritionOf() {
        Nutrition nutrition = Nutrition.of(valueOf(100), valueOf(50), valueOf(30), valueOf(20));
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
        Nutrition base = Nutrition.of(valueOf(101.99), valueOf(57), valueOf(3.1), valueOf(21.12));
        Nutrition addition = Nutrition.of(valueOf(50.4), valueOf(25.7), valueOf(15.9), valueOf(10.08));
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
        Nutrition base = Nutrition.of(valueOf(200.2), valueOf(100.3), valueOf(60.1), valueOf(40.3));
        Nutrition subtraction = Nutrition.of(valueOf(50.34), valueOf(25.99), valueOf(15), valueOf(10.77));
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
        Nutrition base = Nutrition.of(valueOf(100.77), valueOf(51.5), valueOf(30.99), valueOf(20.3));
        Nutrition result = base.multiply(valueOf(10.3));
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
        Nutrition base = Nutrition.of(valueOf(100.55), valueOf(50.1), valueOf(30.05), valueOf(110));
        Nutrition result = base.divide(valueOf(2.2));
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
        Nutrition n1 = Nutrition.of(valueOf(100), valueOf(50), valueOf(30), valueOf(20));
        Nutrition n2 = Nutrition.of(valueOf(100), valueOf(50), valueOf(30), valueOf(20));
        assertAll(
                () -> assertEquals(n1, n2),
                () -> assertEquals(n1.hashCode(), n2.hashCode())
        );
    }
}
