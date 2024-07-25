package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerGram;
import flab.nutridiary.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;


    @DisplayName("상품 등록 서비스 테스트.")
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
        Long savedId = productService.addProduct(product);

        // then
        Assertions.assertThat(savedId).isNotNull();
    }
}