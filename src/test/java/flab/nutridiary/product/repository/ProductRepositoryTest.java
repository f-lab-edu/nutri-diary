package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerGram;
import flab.nutridiary.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 저장 리포지토리 테스트.")
    @Test
    void saveTest() throws Exception {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .calories(BigDecimal.valueOf(100))
                .carbohydrate(BigDecimal.valueOf(10))
                .protein(BigDecimal.valueOf(20)).
                fat(BigDecimal.valueOf(30))
                .servingUnit("컵")
                .servingWeightGram(100)
                .build();

        NutritionFactsPerGram nutritionFactsPerGram = NutritionFactsPerGram.builder()
                .calories(BigDecimal.valueOf(15.2))
                .carbohydrate(BigDecimal.valueOf(10.9))
                .protein(BigDecimal.valueOf(20.21)).
                fat(BigDecimal.valueOf(30))
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .nutritionFactsPerGram(nutritionFactsPerGram)
                .build();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("사과");
        assertThat(savedProduct.getProductCorp()).isEqualTo("사과회사");
        assertThat(savedProduct.getNutritionFacts().getCalories()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(savedProduct.getNutritionFacts().getCarbohydrate()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(savedProduct.getNutritionFacts().getProtein()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(savedProduct.getNutritionFacts().getFat()).isEqualTo(BigDecimal.valueOf(30));
        assertThat(savedProduct.getNutritionFacts().getServingUnit()).isEqualTo("컵");
        assertThat(savedProduct.getNutritionFacts().getServingWeightGram()).isEqualTo(100);
        assertThat(savedProduct.getNutritionFactsPerGram().getCalories()).isEqualTo(BigDecimal.valueOf(15.2));
        assertThat(savedProduct.getNutritionFactsPerGram().getCarbohydrate()).isEqualTo(BigDecimal.valueOf(10.9));
        assertThat(savedProduct.getNutritionFactsPerGram().getProtein()).isEqualTo(BigDecimal.valueOf(20.21));
        assertThat(savedProduct.getNutritionFactsPerGram().getFat()).isEqualTo(BigDecimal.valueOf(30));
    }
}