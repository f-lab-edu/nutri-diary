package flab.nutridiary.product.service;

import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.dto.NewProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRegisterService productRegisterService;


    @DisplayName("상품 등록 서비스 테스트.")
    @Test
    void saveTest() throws Exception {
        // given
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .servingSize(1)
                .servingUnit("컵")
                .servingWeightGram(90)
                .calories(BigDecimal.valueOf(120))
                .carbohydrate(BigDecimal.valueOf(15.5))
                .protein(BigDecimal.valueOf(3.5))
                .fat(BigDecimal.valueOf(5.5))
                .build();

        // when
        NewProductResponse response = productRegisterService.process(newProductRequest);

        // then
        Assertions.assertThat(response.getProductId()).isNotNull();
    }
}