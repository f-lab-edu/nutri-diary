package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.dto.NewProductResponse;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
                .servingSize(BigDecimal.ONE)
                .servingUnit("컵")
                .totalWeightGram(BigDecimal.valueOf(90))
                .calories(BigDecimal.valueOf(120))
                .carbohydrate(BigDecimal.valueOf(15.5))
                .protein(BigDecimal.valueOf(3.5))
                .fat(BigDecimal.valueOf(5.5))
                .build();

        // when
        NewProductResponse result = productRegisterService.process(newProductRequest);
        Product savedProduct = productRepository.findById(result.getProductId()).get();

        // then
        assertThat(savedProduct.getProductName()).isEqualTo(newProductRequest.getProductName());
        assertThat(savedProduct.getProductCorp()).isEqualTo(newProductRequest.getCorpName());
        assertThat(savedProduct.getNutritionFacts().getProductServingSize()).isEqualTo(newProductRequest.getServingSize());
        assertThat(savedProduct.getNutritionFacts().getProductServingUnit()).isEqualTo(newProductRequest.getServingUnit());
        assertThat(savedProduct.getNutritionFacts().getProductTotalWeightGram()).isEqualTo(newProductRequest.getTotalWeightGram());
        assertThat(savedProduct.getNutritionFacts().getProductTotalCalories()).isEqualTo(newProductRequest.getCalories());
        assertThat(savedProduct.getNutritionFacts().getProductTotalCarbohydrate()).isEqualTo(newProductRequest.getCarbohydrate());
        assertThat(savedProduct.getNutritionFacts().getProductTotalProtein()).isEqualTo(newProductRequest.getProtein());
        assertThat(savedProduct.getNutritionFacts().getProductTotalFat()).isEqualTo(newProductRequest.getFat());
    }
}