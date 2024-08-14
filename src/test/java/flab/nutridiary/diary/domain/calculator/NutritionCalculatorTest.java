package flab.nutridiary.diary.domain.calculator;

import flab.nutridiary.diary.domain.CalculatedNutrition;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Transactional
@SpringBootTest
class NutritionCalculatorTest {
    @Autowired
    private NutritionCalculator nutritionCalculator;
    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct1;
    private Product savedProduct2;

    @BeforeEach
    void init() {
        Product product1 = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(NutritionFacts.builder()
                        .productTotalCalories(valueOf(100))
                        .productTotalCarbohydrate(valueOf(10))
                        .productTotalProtein(valueOf(20))
                        .productTotalFat(valueOf(30))
                        .productServingSize(BigDecimal.valueOf(1))
                        .productServingUnit("컵")
                        .productTotalWeightGram(valueOf(100))
                        .build())
                .build();

        Product product2 = Product.builder()
                .productName("바나나")
                .productCorp("바나나회사")
                .nutritionFacts(NutritionFacts.builder()
                        .productTotalCalories(valueOf(105))
                        .productTotalCarbohydrate(valueOf(30))
                        .productTotalProtein(valueOf(5))
                        .productTotalFat(valueOf(1))
                        .productServingSize(BigDecimal.valueOf(1))
                        .productServingUnit("개")
                        .productTotalWeightGram(valueOf(90))
                        .build())
                .build();

        savedProduct1 = productRepository.save(product1);
        savedProduct2 = productRepository.save(product2);
    }

    @DisplayName("gram 단위의 식품 섭취 정보를 기반으로 영양소를 계산한다.")
    @Test
    void gramCalc() throws Exception {
        // given
        ProductIntakeInfo productIntakeInfo = ProductIntakeInfo.builder()
                .productId(savedProduct1.getId())
                .quantity(BigDecimal.valueOf(195))
                .servingUnit("gram")
                .build();

        // when
        CalculatedNutrition calculatedNutrition = nutritionCalculator.calculate(productIntakeInfo);

        // then
        CalculatedNutrition expected = CalculatedNutrition.builder()
                .calories(valueOf(195))
                .carbohydrate(valueOf(19.5))
                .protein(valueOf(39))
                .fat(valueOf(58.5))
                .build();
        Assertions.assertThat(calculatedNutrition).isEqualTo(expected);
    }

    @DisplayName("기본 단위의 식품 섭취 정보를 기반으로 영양소를 계산한다.")
    @Test
    void defaultCalc() throws Exception {
        // given
        ProductIntakeInfo productIntakeInfo = ProductIntakeInfo.builder()
                .productId(savedProduct2.getId())
                .quantity(BigDecimal.valueOf(2))
                .servingUnit("개")
                .build();

        // when
        CalculatedNutrition calculatedNutrition = nutritionCalculator.calculate(productIntakeInfo);

        // then
        CalculatedNutrition expected = CalculatedNutrition.builder()
                .calories(valueOf(210))
                .carbohydrate(valueOf(60))
                .protein(valueOf(10))
                .fat(valueOf(2))
                .build();
        Assertions.assertThat(calculatedNutrition).isEqualTo(expected);
    }

}