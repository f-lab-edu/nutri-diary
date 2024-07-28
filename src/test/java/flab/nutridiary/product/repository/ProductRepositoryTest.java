package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.NutritionFacts;
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
                .productCalories(BigDecimal.valueOf(100))
                .productCarbohydrate(BigDecimal.valueOf(10))
                .productProtein(BigDecimal.valueOf(20))
                .productFat(BigDecimal.valueOf(30))
                .productServingUnit("컵")
                .productServingWeightGram(100)
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("사과");
        assertThat(savedProduct.getProductCorp()).isEqualTo("사과회사");
        assertThat(savedProduct.getNutritionFacts().getProductCalories()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(savedProduct.getNutritionFacts().getProductCarbohydrate()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(savedProduct.getNutritionFacts().getProductProtein()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(savedProduct.getNutritionFacts().getProductFat()).isEqualTo(BigDecimal.valueOf(30));
        assertThat(savedProduct.getNutritionFacts().getProductServingUnit()).isEqualTo("컵");
        assertThat(savedProduct.getNutritionFacts().getProductServingWeightGram()).isEqualTo(100);
    }
}