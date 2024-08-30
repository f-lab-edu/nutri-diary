package flab.nutridiary.product.service;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.dto.NewProductResponse;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.TWO;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductRegisterServiceTest {

    @Autowired
    private ProductRegisterService productRegisterService;

    @Autowired
    private ProductRepository productRepository;


    @DisplayName("상품 저장 서비스 테스트.")
    @Test
    void saveTest() throws Exception {
        // given
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .productDefaultServingSize(TWO)
                .productDefaultServingUnit("컵")
                .productTotalWeightGram(valueOf(90))
                .calories(valueOf(120))
                .carbohydrate(valueOf(15.5))
                .protein(valueOf(3.5))
                .fat(valueOf(5.5))
                .build();

        // when
        NewProductResponse result = productRegisterService.process(newProductRequest);

        // then
        Product savedProduct = productRepository.findById(result.getProductId()).get();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("상품명");
        assertThat(savedProduct.getProductCorp()).isEqualTo("업체명");
        assertThat(savedProduct.getNutritionFacts().getNutritionPerOneServingUnit()).isEqualTo(Nutrition.of(valueOf(60), valueOf(7.75), valueOf(1.75), valueOf(2.75)));
    }

    @DisplayName("상품 저장 시 유요한 단위는 2가지가 저장된다.")
    @Test
    void validServingUnit() throws Exception {
        // given
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .productDefaultServingSize(TWO)
                .productDefaultServingUnit("컵")
                .productTotalWeightGram(valueOf(90))
                .calories(valueOf(120))
                .carbohydrate(valueOf(15.5))
                .protein(valueOf(3.5))
                .fat(valueOf(5.5))
                .build();

        // when
        NewProductResponse result = productRegisterService.process(newProductRequest);

        // then
        Product savedProduct = productRepository.findById(result.getProductId()).get();
        assertThat(savedProduct.getNutritionFacts().getAllowedProductServingUnits())
                .isEqualTo(new ArrayList<>(
                        List.of(ServingUnit.asOneServingUnit("컵"),
                                ServingUnit.ofGram(TWO, BigDecimal.valueOf(90)))));
    }
}