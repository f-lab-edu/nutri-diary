package flab.nutridiary.product.service;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.dto.NewProductResponse;
import flab.nutridiary.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

//@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductRegisterServiceTest {

    @Autowired
    private ProductRegisterService productRegisterService;

    @Autowired
    private ProductRepository productRepository;


    @DisplayName("상품 등록 서비스 테스트.")
    @Test
    void saveTest() throws Exception {
        // given
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .servingSize(BigDecimal.TWO)
                .servingUnit("컵")
                .totalWeightGram(BigDecimal.valueOf(90))
                .calories(BigDecimal.valueOf(120))
                .carbohydrate(BigDecimal.valueOf(15.5))
                .protein(BigDecimal.valueOf(3.5))
                .fat(BigDecimal.valueOf(5.5))
                .build();

        // when
        NewProductResponse result = productRegisterService.process(newProductRequest);

        // then
        Product savedProduct = productRepository.findById(result.getProductId()).get();
        Assertions.assertThat(savedProduct.getId()).isNotNull();
        Assertions.assertThat(savedProduct.getProductName()).isEqualTo("상품명");
        Assertions.assertThat(savedProduct.getProductCorp()).isEqualTo("업체명");
        Assertions.assertThat(savedProduct.getNutritionFacts().getProductServingSize()).isEqualTo(BigDecimal.TWO);
        Assertions.assertThat(savedProduct.getNutritionFacts().getProductServingUnit()).isEqualTo("컵");
        Assertions.assertThat(savedProduct.getNutritionFacts().getProductTotalWeightGram()).isEqualTo(BigDecimal.valueOf(90));
        Assertions.assertThat(savedProduct.getNutritionFacts().getTotalNutrition()).isEqualTo(Nutrition.of(120, 15.5, 3.5, 5.5));
    }
}