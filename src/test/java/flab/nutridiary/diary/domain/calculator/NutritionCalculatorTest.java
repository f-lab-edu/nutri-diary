package flab.nutridiary.diary.domain.calculator;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.NutritionCalculator;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static java.math.BigDecimal.valueOf;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class NutritionCalculatorTest {
    @Autowired
    private NutritionCalculator nutritionCalculator;
    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct1;

    @BeforeEach
    void init() {
        Product product1 = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(NutritionFacts.builder()
                        .totalNutrition(Nutrition.of(valueOf(195), valueOf(19.5), valueOf(39), valueOf(58.5)))
                        .productServingSize(valueOf(1))
                        .productServingUnit("개")
                        .productTotalWeightGram(valueOf(100))
                        .build())
                .build();

        savedProduct1 = productRepository.save(product1);
    }

    @DisplayName("gram 단위의 식품 섭취 정보 기반으로 영양소를 계산한다.")
    @Test
    void gramCalc() throws Exception {
        // given
        ProductIntakeInfo productIntakeInfo = ProductIntakeInfo.builder()
                .productId(savedProduct1.getId())
                .quantity(valueOf(195))
                .mealType("BREAKFAST")
                .servingUnit("gram")
                .build();

        // when
        Nutrition calculatedNutrition = nutritionCalculator.calculate(productIntakeInfo);

        // then
        Nutrition expected = Nutrition.of(valueOf(380.25), valueOf(39), valueOf(76.05), valueOf(115.05));
        Assertions.assertThat(calculatedNutrition).isEqualTo(expected);
    }

    @DisplayName("기본 단위의 식품 섭취 정보 기반으로 영양소를 계산한다.")
    @Test
    void defaultCalc() throws Exception {
        // given
        ProductIntakeInfo productIntakeInfo = ProductIntakeInfo.builder()
                .productId(savedProduct1.getId())
                .quantity(valueOf(2))
                .mealType("BREAKFAST")
                .servingUnit("개")
                .build();

        // when
        Nutrition calculatedNutrition = nutritionCalculator.calculate(productIntakeInfo);

        // then
        Nutrition expected = Nutrition.of(valueOf(390), valueOf(39), valueOf(78), valueOf(117));
        Assertions.assertThat(calculatedNutrition).isEqualTo(expected);
    }
}