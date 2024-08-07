package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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
                .productTotalCalories(valueOf(100))
                .productTotalCarbohydrate(valueOf(10))
                .productTotalProtein(valueOf(20))
                .productTotalFat(valueOf(30))
                .productServingUnit("컵")
                .productTotalWeightGram(valueOf(100))
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
        assertThat(savedProduct.getNutritionFacts().getProductTotalCalories()).isEqualTo(valueOf(100));
        assertThat(savedProduct.getNutritionFacts().getProductTotalCarbohydrate()).isEqualTo(valueOf(10));
        assertThat(savedProduct.getNutritionFacts().getProductTotalProtein()).isEqualTo(valueOf(20));
        assertThat(savedProduct.getNutritionFacts().getProductTotalFat()).isEqualTo(valueOf(30));
        assertThat(savedProduct.getNutritionFacts().getProductServingUnit()).isEqualTo("컵");
        assertThat(savedProduct.getNutritionFacts().getProductTotalWeightGram()).isEqualTo(valueOf(100));
    }

    @DisplayName("상품 조회 리포지토리 테스트.")
    @Test
    void findById() throws Exception {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .productTotalCalories(valueOf(100))
                .productTotalCarbohydrate(valueOf(10))
                .productTotalProtein(valueOf(20))
                .productTotalFat(valueOf(30))
                .productServingUnit("컵")
                .productTotalWeightGram(valueOf(100))
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        Product savedProduct = productRepository.save(product);
        Long savedProductId = savedProduct.getId();

        // when
        Product foundProduct = productRepository.findById(savedProductId).get();

        // then
        assertThat(foundProduct.getId()).isEqualTo(savedProductId);
        assertThat(foundProduct.getProductName()).isEqualTo("사과");
        assertThat(foundProduct.getProductCorp()).isEqualTo("사과회사");
        assertThat(foundProduct.getNutritionFacts().getProductTotalCalories()).isEqualTo(valueOf(100));
        assertThat(foundProduct.getNutritionFacts().getProductTotalCarbohydrate()).isEqualTo(valueOf(10));
        assertThat(foundProduct.getNutritionFacts().getProductTotalProtein()).isEqualTo(valueOf(20));
        assertThat(foundProduct.getNutritionFacts().getProductTotalFat()).isEqualTo(valueOf(30));
        assertThat(foundProduct.getNutritionFacts().getProductServingUnit()).isEqualTo("컵");
        assertThat(foundProduct.getNutritionFacts().getProductTotalWeightGram()).isEqualTo(valueOf(100));
    }
}