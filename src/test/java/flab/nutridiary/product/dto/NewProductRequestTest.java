package flab.nutridiary.product.dto;

import flab.nutridiary.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

class NewProductRequestTest {

    @DisplayName("상품 저장 요청 DTO 그램 변환 테스트.")
    @Test
    void toProduct() throws Exception {
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
        Product product = newProductRequest.toProduct();

        // then
        assertThat(product.getProductName()).isEqualTo("상품명");
        assertThat(product.getProductCorp()).isEqualTo("업체명");
        assertThat(product.getNutritionFactsPerGram().getCalories()).isEqualTo(BigDecimal.valueOf(120).divide(BigDecimal.valueOf(90), 2, RoundingMode.HALF_UP));
        assertThat(product.getNutritionFactsPerGram().getCarbohydrate()).isEqualTo(BigDecimal.valueOf(15.5).divide(BigDecimal.valueOf(90), 2, RoundingMode.HALF_UP));
        assertThat(product.getNutritionFactsPerGram().getProtein()).isEqualTo(BigDecimal.valueOf(3.5).divide(BigDecimal.valueOf(90), 2, RoundingMode.HALF_UP));
        assertThat(product.getNutritionFactsPerGram().getFat()).isEqualTo(BigDecimal.valueOf(5.5).divide(BigDecimal.valueOf(90), 2, RoundingMode.HALF_UP));

    }

}