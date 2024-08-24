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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static java.math.BigDecimal.TWO;
import static java.math.BigDecimal.valueOf;

@ActiveProfiles("test")
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
                .servingSize(TWO)
                .servingUnit("컵")
                .totalWeightGram(valueOf(90))
                .calories(valueOf(120))
                .carbohydrate(valueOf(15.5))
                .protein(valueOf(3.5))
                .fat(valueOf(5.5))
                .build();

        // when
        NewProductResponse result = productRegisterService.process(newProductRequest);

        // then
        Product savedProduct = productRepository.findById(result.getProductId()).get();
        Assertions.assertThat(savedProduct.getId()).isNotNull();
        Assertions.assertThat(savedProduct.getProductName()).isEqualTo("상품명");
        Assertions.assertThat(savedProduct.getProductCorp()).isEqualTo("업체명");
        Assertions.assertThat(savedProduct.getNutritionFacts().getProductServingSize()).isEqualTo(TWO);
        Assertions.assertThat(savedProduct.getNutritionFacts().getProductServingUnit()).isEqualTo("컵");
        Assertions.assertThat(savedProduct.getNutritionFacts().getProductTotalWeightGram()).isEqualTo(valueOf(90));
        Assertions.assertThat(savedProduct.getNutritionFacts().getTotalNutrition()).isEqualTo(Nutrition.of(valueOf(120), valueOf(15.5), valueOf(3.5), valueOf(5.5)));
    }
}